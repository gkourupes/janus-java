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

module fx {
	requires static lombok;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires org.apache.commons.lang3;
	requires org.apache.commons.configuration2;
	requires java.desktop;
	requires java.prefs;
	requires java.naming;
	requires jakarta.annotation;
	requires jakarta.cdi;
	requires jakarta.inject;
	requires jakarta.json.bind;
	requires jakarta.json;
	requires jakarta.ws.rs;
	requires jakarta.xml.bind;
	requires com.sun.jna.platform;
	requires com.sun.jna;
	requires jersey.client;
	requires org.kordamp.ikonli.javafx;
	requires org.eclipse.yasson;
	requires com.fasterxml.jackson.databind;
	requires jsch;
	requires java.sql;
	requires java.instrument;
	requires org.controlsfx.controls;
	requires org.update4j;
	requires org.apache.commons.io;
	requires jersey.media.jaxb;
	requires java.xml;
	requires org.kordamp.ikonli.fontawesome5;
	requires weld.environment.common;
	requires weld.se.core;
	requires weld.core.impl;
	requires weld.api;
	requires jersey.common;
	requires org.slf4j;
	requires ch.qos.logback.core;
	requires ch.qos.logback.classic;
	//requires FXTrayIcon;
	//requires javafx.swing;
	requires j8fu;
	requires web3jshaded;

	uses org.update4j.service.Launcher;
	provides org.update4j.service.Launcher with org.unigrid.janus.JanusLauncher;

	opens org.unigrid.janus.view.backing to weld.core.impl;
	opens org.unigrid.janus to weld.core.impl, org.update4j;//, javafx.swing;
	opens org.unigrid.janus.controller.component to javafx.fxml, javafx.base, javafx.controls, org.update4j, javafx.graphics,
		weld.core.impl;
	opens org.unigrid.janus.controller to javafx.fxml, weld.core.impl, javafx.base, javafx.controls, org.update4j,
		javafx.graphics;
	opens org.unigrid.janus.view to weld.core.impl;
	opens org.unigrid.janus.view.component to weld.core.impl, javafx.fxml, javafx.base, javafx.controls;
	opens org.unigrid.janus.model to weld.core.impl, javafx.base , jakarta.xml.bind, jakarta.ws.rs, com.fasterxml.jackson.databind,
		jersey.media.jaxb;
	opens org.unigrid.janus.model.rpc.entity to weld.core.impl, org.eclipse.yasson;
	opens org.unigrid.janus.model.service to weld.core.impl, org.update4j, org.apache.commons.configuration2;//, javafx.swing;
	opens org.unigrid.janus.model.entity to jakarta.xml.bind, jakarta.ws.rs, jersey.media.jaxb;
	opens org.unigrid.janus.model.rest.entity to weld.core.impl, org.eclipse.yasson, jakarta.xml.bind, jakarta.ws.rs, jersey.media.jaxb;

	exports org.unigrid.janus;
	
	exports org.unigrid.janus.controller.component to weld.core.impl, javafx.fxml, javafx.base,
		javafx.controls, org.update4j, javafx.graphics;
	exports org.unigrid.janus.controller to weld.core.impl ,javafx.fxml, javafx.base, javafx.controls,
		org.update4j, javafx.graphics;
	exports org.unigrid.janus.model.signal to weld.core.impl;
	exports org.unigrid.janus.model.producer to weld.core.impl;
	exports org.unigrid.janus.model.setup to weld.core.impl;
	exports org.unigrid.janus.model.rpc to weld.core.impl;
	exports org.unigrid.janus.view.component to weld.core.impl;
	exports org.unigrid.janus.view.decorator to weld.core.impl;
	exports org.unigrid.janus.model to org.eclipse.yasson, com.fasterxml.jackson.databind;
}
