/*
 * Copyright 2016, 2017 Peter Doornbosch
 *
 * This file is part of JMeter-WebSocket-Samplers, a JMeter add-on for load-testing WebSocket applications.
 *
 * JMeter-WebSocket-Samplers is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * JMeter-WebSocket-Samplers is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.luminis.jmeter.wssampler;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import static eu.luminis.jmeter.wssampler.WebsocketSampler.MAX_READ_TIMEOUT;
import static eu.luminis.jmeter.wssampler.WebsocketSampler.MIN_READ_TIMEOUT;
import static javax.swing.BoxLayout.*;

public class CloseWebSocketSamplerGui extends AbstractSamplerGui {

    private CloseWebSocketSamplerGuiPanel settingsPanel;

    public CloseWebSocketSamplerGui() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        settingsPanel = new CloseWebSocketSamplerGuiPanel();
        add(settingsPanel, BorderLayout.CENTER);
    }

    @Override
    public String getStaticLabel() {
        return "WebSocket Close";
    }

    @Override
    public String getLabelResource() {
        return null;
    }

    @Override
    public void clearGui() {
        super.clearGui();
        settingsPanel.clearGui();
    }

    @Override
    public TestElement createTestElement() {
        CloseWebSocketSampler element = new CloseWebSocketSampler();
        configureTestElement(element);  // Essential because it sets some basic JMeter properties (e.g. the link between sampler and gui class)
        return element;
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (element instanceof CloseWebSocketSampler) {
            CloseWebSocketSampler sampler = (CloseWebSocketSampler) element;
            settingsPanel.closeStatusField.setText(sampler.getStatusCode());
            settingsPanel.readTimeoutField.setText(sampler.getReadTimeout());
            settingsPanel.enableConnectionIdOption((WebsocketSampler.multipleConnectionsEnabled));
            settingsPanel.connectionIdField.setText(sampler.getConnectionId());
        }
        super.configure(element);
    }

    @Override
    public void modifyTestElement(TestElement testElement) {
        configureTestElement(testElement);
        if (testElement instanceof CloseWebSocketSampler) {
            CloseWebSocketSampler sampler = (CloseWebSocketSampler) testElement;
            sampler.setStatusCode(settingsPanel.closeStatusField.getText());
            sampler.setReadTimeout(settingsPanel.readTimeoutField.getText());
            sampler.setConnectionId(settingsPanel.connectionIdField.getText());
        }
    }
}

