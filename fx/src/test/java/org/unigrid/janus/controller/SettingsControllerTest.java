/*
	The Janus Wallet
	Copyright © 2021-2022 The Unigrid Foundation, UGD Software AB

	This program is free software: you can redistribute it and/or modify it under the terms of the
	addended GNU Affero General Public License as published by the Free Software Foundation, version 3
	of the License (see COPYING and COPYING.addendum).

	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
	even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
	GNU Affero General Public License for more details.

	You should have received an addended copy of the GNU Affero General Public License with this program.
	If not, see <http://www.gnu.org/licenses/> and <https://github.com/unigrid-project/janus-java>.
 */

package org.unigrid.janus.controller;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.input.KeyCode;
import mockit.Mock;
import mockit.Mocked;
import net.jqwik.api.Example;
import net.jqwik.api.lifecycle.BeforeContainer;
import static org.awaitility.Awaitility.await;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.unigrid.janus.jqwik.fx.BaseFxTest;
import org.unigrid.janus.jqwik.fx.FxResource;
import org.unigrid.janus.model.Wallet;
import org.unigrid.janus.model.external.JaxrsResponseHandler;
import org.unigrid.janus.model.external.ResponseMockUp;
import org.unigrid.janus.model.rpc.entity.DumpWallet;
import org.unigrid.janus.model.rpc.entity.GetBlockCount;
import org.unigrid.janus.model.rpc.entity.GetConnectionCount;
import org.unigrid.janus.model.rpc.entity.GetUnlockState;
import org.unigrid.janus.model.rpc.entity.GetWalletInfo;
import org.unigrid.janus.model.rpc.entity.GridnodeList;
import org.unigrid.janus.model.rpc.entity.ListAddressBalances;
import org.unigrid.janus.model.rpc.entity.ListTransactions;
import org.unigrid.janus.model.rpc.entity.StakingStatus;
import org.unigrid.janus.model.rpc.entity.UpdatePassphrase;
import org.unigrid.janus.model.rpc.entity.BackupWallet;
import org.unigrid.janus.model.service.DaemonMockUp;
import org.unigrid.janus.model.FileBasedConfigurationMockup;
import org.unigrid.janus.model.rpc.entity.Info;
import org.unigrid.janus.model.service.DebugService;
import org.unigrid.janus.model.service.RPCService;
import org.unigrid.janus.model.service.external.FileChooserMockUp;
import org.unigrid.janus.model.service.external.JerseyInvocationMockUp;
import org.unigrid.janus.model.service.external.WebTargetMockUp;
import org.unigrid.janus.view.MainWindow;

@FxResource(clazz = MainWindow.class, name = "mainWindow.fxml")
public class SettingsControllerTest extends BaseFxTest {
	@Inject
	private FxRobot robot;

	@Inject
	private RPCService rpc;

	@Inject
	private Wallet wallet;

	@Mocked
	private DebugService debug;

	@BeforeContainer
	public static void before() {
		new JerseyInvocationMockUp();
		new WebTargetMockUp();
		new FileBasedConfigurationMockup();
		new DaemonMockUp();

		new ResponseMockUp() {
			@Mock
			public <T> T readEntity(Class<T> clazz) {
				T e = readEntities(clazz);
				if (Objects.isNull(e)) {
					if (clazz.equals(StakingStatus.class)) {
						return (T) JaxrsResponseHandler.handle(StakingStatus.class,
							StakingStatus.Result.class,
							() -> "staking_status_not_staking.json");
					}
					if (clazz.equals(GetWalletInfo.class)) {
						return (T) JaxrsResponseHandler.handle(GetWalletInfo.class,
							GetWalletInfo.Result.class,
							() -> "get_wallet_info_unlocked.json");
					}
					if (clazz.equals(Info.class)) {
						return (T) JaxrsResponseHandler.handle(Info.class,
							Info.Result.class,
							() -> "get_info.json");
					}
					if (clazz.equals(GetUnlockState.class)) {
						return (T) JaxrsResponseHandler.handle(GetUnlockState.class,
							GetUnlockState.Result.class, () -> "get_unlock_state.json");
					}
					if (clazz.equals(ListTransactions.class)) {
						return (T) JaxrsResponseHandler.handle(ListTransactions.class,
							new ArrayList<ListTransactions.Result>() {
							}.getClass().getGenericSuperclass(),
							() -> "list_transactions.json");
					}
					if (clazz.equals(GridnodeList.class)) {
						return (T) JaxrsResponseHandler.handle(GridnodeList.class,
							new ArrayList<GridnodeList.Result>() {
							}.getClass().getGenericSuperclass(),
							() -> "list_gridnodes_outputs.json");
					}
					if (clazz.equals(ListAddressBalances.class)) {
						return (T) JaxrsResponseHandler.handle(ListAddressBalances.class,
							new ArrayList<ListAddressBalances.Result>() {
							}.getClass().getGenericSuperclass(),
							() -> "list_address_balances.json");
					}
					if (clazz.equals(GetBlockCount.class)) {
						return (T) JaxrsResponseHandler.handle(GetBlockCount.class,
							Integer.class, () -> "get_block_count.json");
					}
					if (clazz.equals(GetConnectionCount.class)) {
						return (T) JaxrsResponseHandler.handle(GetConnectionCount.class,
							Integer.class, () -> "getconnectioncount.json");
					}
					if (clazz.equals(UpdatePassphrase.class)) {
						return (T) new UpdatePassphrase();
					}
					if (clazz.equals(DumpWallet.class)) {
						return (T) new DumpWallet();
					}
					if (clazz.equals(BackupWallet.class)) {
						return (T) new BackupWallet();
					}
				}

				return e;
			}
		};
	}

	@Example
	public void shouldSwitchTabs() {
		robot.clickOn("#btnSettings");
		verifyThat("#pnlSetGeneral", isVisible());

		robot.clickOn("#btnSetDebug");
		verifyThat("#pnlSetDebug", isVisible());

		robot.clickOn("#btnSetExport");
		verifyThat("#btnSetExport", isVisible());

		robot.clickOn("#btnSetPassphrase");
		verifyThat("#pnlSetPassphrase", isVisible());

		robot.clickOn("#btnSetGeneral");
		verifyThat("#pnlSetGeneral", isVisible());
	}

	@Example
	public void shouldChangePassword() {
		rpc.pollForInfo(900000);
		await().until(() -> wallet != null && wallet.getStakingStatus() != null);
		rpc.stopPolling();

		robot.clickOn("#btnSettings");
		robot.clickOn("#btnSetPassphrase");
		robot.clickOn("#taPassphrase");
		robot.write("hello");
		robot.clickOn("#taRepeatPassphrase");
		robot.write("W0rld");
		robot.clickOn("#btnUpdatePassphrase");
		robot.type(KeyCode.ENTER);
		verifyThat("#pnlWallet", isVisible());
	}

	@Example
	public void shouldAskForFile() {
		new FileChooserMockUp();

		robot.clickOn("#btnSettings");
		robot.clickOn("#btnSetExport");

		robot.clickOn("#btnSetExportImport");
		robot.clickOn("#btnSetExportBackup");

		wallet.setLocked(Boolean.FALSE);
		robot.clickOn("#btnSetExportExport");
	}

}
