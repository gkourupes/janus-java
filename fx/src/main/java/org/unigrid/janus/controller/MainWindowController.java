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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javafx.animation.FadeTransition;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.unigrid.janus.model.service.DebugService;
import org.unigrid.janus.model.service.RPCService;
import org.unigrid.janus.model.service.WindowService;
import org.unigrid.janus.model.Wallet;
import org.unigrid.janus.model.rpc.entity.LockWallet;
import org.kordamp.ikonli.javafx.FontIcon;

@ApplicationScoped
public class MainWindowController implements Initializable, PropertyChangeListener {
	@Inject private DebugService debug;
	@Inject private RPCService rpc;
	@Inject private Wallet wallet;

	private static WindowService window = WindowService.getInstance();
	private static WarningController warning = new WarningController();
	private static final int TAB_WALLET = 1;
	private static final int TAB_TRANSACTIONS = 2;
	private static final int TAB_NODES = 3;
	private static final int TAB_ADDRESS = 4;
	private static final int TAB_SETTINGS = 5;
	private static final int TAB_DOCS = 6;

	// @FXML private Label lblBlockCount;
	// @FXML private Label lblConnection;
	@FXML private AnchorPane pnlMain;
	@FXML private AnchorPane pnlSplash;
	@FXML private FontIcon blocksIcn;
	@FXML private ToggleButton btnWallet;
	@FXML private ToggleButton btnTransactions;
	@FXML private ToggleButton btnNodes;
	@FXML private ToggleButton btnAddress;
	@FXML private ToggleButton btnDocs;
	@FXML private ToggleButton btnSettings;
	@FXML private VBox pnlWallet;
	@FXML private VBox pnlTransactions;
	@FXML private VBox pnlNodes;
	@FXML private VBox pnlAddress;
	@FXML private VBox pnlSettings;
	@FXML private VBox pnlDocs;
	@FXML private AnchorPane pnlOverlay;
	@FXML private AnchorPane pnlWarning;
	@FXML private FontIcon lockBtn;
	@FXML private FontIcon satelliteIcn;
	@FXML private FontIcon coinsBtn;
	@FXML private FontIcon unlockedBtn;
	@FXML private Tooltip connectionTltp;
	@FXML private Tooltip lockedTltp;
	@FXML private Tooltip stakingTltp;
	@FXML private Tooltip blocksTltp;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		hideOverlay();
		hideWarning();
		wallet.addPropertyChangeListener(this);
		window.setMainWIndowController(this);
	}

	@FXML
	private void onShown(WindowEvent event) {
		debug.log("Shown event fired!");
		lockBtn.iconColorProperty().setValue(Color.RED);

		Platform.runLater(() -> {
			try {
				debug.log("Shown event executing.");
				window.getTransactionsController().onShown();
				//window.getAddressController().onShown();
				// testing
				//window.getWindowBarController().startSpinner();
			} catch (Exception e) {
				debug.log(String.format("ERROR: (onShown) %s", e.getMessage()));
			}
		});
	}

	public void tabSelect(int tab) {
		btnWallet.setSelected(false);
		btnTransactions.setSelected(false);
		btnNodes.setSelected(false);
		btnSettings.setSelected(false);
		pnlWallet.setVisible(false);
		pnlTransactions.setVisible(false);
		pnlNodes.setVisible(false);
		pnlSettings.setVisible(false);
		pnlAddress.setVisible(false);
		btnAddress.setSelected(false);
		btnDocs.setSelected(false);
		pnlDocs.setVisible(false);

		switch (tab) {
			case TAB_WALLET:
				pnlWallet.setVisible(true);
				btnWallet.setSelected(true);
				break;
			case TAB_TRANSACTIONS:
				pnlTransactions.setVisible(true);
				btnTransactions.setSelected(true);
				break;
			case TAB_NODES:
				pnlNodes.setVisible(true);
				btnNodes.setSelected(true);
				break;
			case TAB_ADDRESS:
				pnlAddress.setVisible(true);
				btnAddress.setSelected(true);
				break;
			case TAB_SETTINGS:
				pnlSettings.setVisible(true);
				btnSettings.setSelected(true);
				break;
			case TAB_DOCS:
				pnlDocs.setVisible(true);
				btnDocs.setSelected(true);
				break;
			default:
				pnlWallet.setVisible(true);
				btnWallet.setSelected(true);
				break;
		}

	}

	@FXML
	private void onWalletTap(MouseEvent event) {
		try {
			tabSelect(TAB_WALLET);
		} catch (Exception e) {
			debug.log(String.format("ERROR: (wallet click) %s",  e.getMessage()));
		}
	}

	@FXML
	private void onTransactionsTap(MouseEvent event) {
		try {
			tabSelect(TAB_TRANSACTIONS);
		} catch (Exception e) {
			debug.log(String.format("ERROR: (transactions click) %s",  e.getMessage()));
		}
	}

	@FXML
	private void onNodesTap(MouseEvent event) {
		try {
			tabSelect(TAB_NODES);
		} catch (Exception e) {
			debug.log(String.format("ERROR: (nodes click) %s",  e.getMessage()));
		}
	}

	@FXML
	private void onAddressTap(MouseEvent event) {
		try {
			tabSelect(TAB_ADDRESS);
		} catch (Exception e) {
			debug.log(String.format("ERROR: (address click) %s",  e.getMessage()));
		}
	}

	@FXML
	private void onDocsClicked(MouseEvent event) {
		try {
			tabSelect(TAB_DOCS);
		} catch (Exception e) {
			debug.print(String.format("ERROR: (docs click) %s",  e.getMessage()),
				MainWindowController.class.getSimpleName()
			);
		}
	}

	@FXML
	private void onSettingsTap(MouseEvent event) {
		try {
			tabSelect(TAB_SETTINGS);
		} catch (Exception e) {
			debug.log(String.format("ERROR: (settings click) %s",  e.getMessage()));
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
		//debug.log("Property changed:");
		//debug.log(event.getPropertyName());

		if (event.getPropertyName().equals(wallet.BLOCKS_PROPERTY)) {
			String blocks;
			if (wallet.getSyncStatus() == Wallet.SyncStatus.SYNCING) {
				blocks = String.format("Syncing / Blocks: %d", (int) event.getNewValue());
			} else {
				blocks = String.format("Blocks: %d", (int) event.getNewValue());
			}

			blocksTltp.setText(blocks);
		}

		if (event.getPropertyName().equals(wallet.CONNECTIONS_PROPERTY)) {
			connectionTltp.setText(String.format("Connections: %d", (int) event.getNewValue()));
			int connections = (int) event.getNewValue();

			if (connections > 0 && connections < 5) {
				satelliteIcn.iconColorProperty().setValue(Color.ORANGE);
			} else if (connections >= 5 && connections < 10) {
				satelliteIcn.iconColorProperty().setValue(Color.YELLOWGREEN);
			} else if (connections >= 10) {
				satelliteIcn.iconColorProperty().setValue(Color.GREEN);
			} else {
				satelliteIcn.iconColorProperty().setValue(Color.RED);
			}
		}

		if (event.getPropertyName().equals(wallet.LOCKED_PROPERTY)) {
			boolean locked = (boolean) event.getNewValue();
			debug.log(String.format("Wallet Locked: %s", locked));

			if (locked) {
				//tabSelect(TAB_WALLET);
				//show locked icon
				lockedTltp.setText("Wallet Locked");
				unlockedBtn.setVisible(false);
				lockBtn.setVisible(true);
			} else {
				// show unlock icon
				lockedTltp.setText("Wallet Unlocked");
				unlockedBtn.setVisible(true);
				lockBtn.setVisible(false);
			}

		}
		if (event.getPropertyName().equals(wallet.LOCKED_STATE_PROPERTY)) {
			Wallet.LockState lockedState = (Wallet.LockState) event.getNewValue();
			System.out.println(lockedState);

			if (lockedState.equals(Wallet.LockState.UNLOCKED_FOR_STAKING) && !wallet.getStakingStatus()) {
				FadeTransition ft = new FadeTransition(Duration.millis(500), coinsBtn);
				ft.setFromValue(1.0);
				ft.setToValue(0.3);
				ft.setCycleCount(40);
				ft.setAutoReverse(true);
				ft.play();
			}
		}

		if (event.getPropertyName().equals(wallet.SYNC_STATE)) {
			Wallet.SyncStatus syncStatus = (Wallet.SyncStatus) event.getNewValue();
			System.out.println("sync state: " + syncStatus);
			if (syncStatus.equals(Wallet.SyncStatus.SYNCING)) {
				blocksIcn.iconColorProperty().setValue(Color.RED);
				FadeTransition ft = new FadeTransition(Duration.millis(500), blocksIcn);
				ft.setFromValue(1.0);
				ft.setToValue(0.3);
				ft.setCycleCount(40);
				ft.setAutoReverse(true);
				ft.play();
			} else {
				blocksIcn.iconColorProperty().setValue(Color.web("#68c5ff"));
			}
		}

		if (event.getPropertyName().equals(wallet.STAKING_PROPERTY)) {
			boolean staking = (boolean) event.getNewValue();

			if (staking) {
				stakingTltp.setText("Staking On");
				coinsBtn.iconColorProperty().setValue(Color.ORANGE);
			} else {
				stakingTltp.setText("Staking Off");
				coinsBtn.iconColorProperty().setValue(Color.RED);
			}
		}

		if (event.getPropertyName().equals(wallet.IS_OFFLINE)) {
			System.out.println("wallet.IS_OFFLINE");

			if (wallet.getOffline()) {
				showWarning();
			}
		}

		/*
		if (event.getPropertyName().equals(wallet.STATUS_PROPERTY)) {
			String status = (String) event.getNewValue();
			if (status.equals("Done loading")) {
				pnlSplash.setVisible(false);
			} else {
				pnlSplash.setVisible(true);
			}
		}
		 */
	}

	public void showOverlay() {
		pnlOverlay.setVisible(true);
	}

	public void hideOverlay() {
		pnlOverlay.setVisible(false);
	}

	public void showWarning() {
		pnlWarning.setVisible(true);
	}

	public void hideWarning() {
		pnlWarning.setVisible(false);
	}

	public void showSplash() {
		pnlSplash.setVisible(true);
	}

	public void hideSpalsh() {
		pnlSplash.setVisible(false);
	}

	@FXML
	private void onLockPressed(MouseEvent event) {
		if (!wallet.getLocked()) {
			return;
		}

		window.getOverlayController().startLockOverlay();
		showOverlay();
	}

	@FXML
	private void onUnlockPressed(MouseEvent event) {
		debug.print("Locking wallet pressed", MainWindowController.class.getSimpleName());

		if (wallet.getLocked()) {
			return;
		}

		wallet.setLocked(Boolean.TRUE);
		final LockWallet call = rpc.call(new LockWallet.Request(), LockWallet.class);
		debug.print("Locking wallet", MainWindowController.class.getSimpleName());
	}

	@FXML
	private void onCoinsPressed(MouseEvent event) {
		if (wallet.getStakingStatus() || !wallet.getLocked()) {
			return;
		}

		window.getOverlayController().startStakingOverlay();
		showOverlay();
	}

	public void unlockForTime() {
		window.getOverlayController().startUnlockForTimeOverlay();
		showOverlay();
	}

	public void unlockForSending() {
		window.getOverlayController().startUnlockForSendingOverlay();
		showOverlay();
	}

	public void unlockForGridnode() {
		window.getOverlayController().startUnlockForGridnodeOverlay();
		showOverlay();
	}

	public void unlockForDump() {
		window.getOverlayController().startUnlockForDump();
		showOverlay();
	}
}
