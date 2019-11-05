/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.dma.ais.bus.tcp;

import java.net.Socket;

import dk.dma.ais.packet.AisPacket;
import java.util.function.Consumer;

/**
 * TCP server for reading clients
 */
public class TcpReadServer extends TcpServer {
    
    private final Consumer<AisPacket> packetConsumer;

    /**
     * Instantiates a new Tcp read server.
     *
     * @param packetConsumer the packet consumer
     */
    public TcpReadServer(Consumer<AisPacket> packetConsumer) {
        super();
        this.packetConsumer = packetConsumer;
    }

    @Override
    protected TcpReadClient newClient(Socket socket) {
        return new TcpReadClient(packetConsumer, this, socket, clientConf);
    }

}
