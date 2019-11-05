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
package dk.dma.ais.configuration.bus.provider;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import dk.dma.ais.bus.AisBusComponent;
import dk.dma.ais.bus.provider.RepeatingFileReaderProvider;

/**
 * The type Repeating file reader provider configuration.
 */
@XmlRootElement
public class RepeatingFileReaderProviderConfiguration extends AisBusProviderConfiguration {

    private String filename;
    private boolean gzip;

    /**
     * Instantiates a new Repeating file reader provider configuration.
     */
    public RepeatingFileReaderProviderConfiguration() {

    }

    /**
     * Gets filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets filename.
     *
     * @param filename the filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Is gzip boolean.
     *
     * @return the boolean
     */
    public boolean isGzip() {
        return gzip;
    }

    /**
     * Sets gzip.
     *
     * @param gzip the gzip
     */
    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }

    @Override
    @XmlTransient
    public AisBusComponent getInstance() {
        RepeatingFileReaderProvider provider = new RepeatingFileReaderProvider(filename, gzip);
        return super.configure(provider);
    }

}
