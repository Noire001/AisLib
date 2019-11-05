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
package dk.dma.ais.packet;

import java.io.Serializable;
import java.util.Date;

import net.jcip.annotations.NotThreadSafe;
import dk.dma.ais.proprietary.IProprietarySourceTag;
import dk.dma.ais.proprietary.IProprietaryTag;
import dk.dma.ais.sentence.CommentBlock;
import dk.dma.ais.sentence.Vdm;
import dk.dma.enav.model.Country;

/**
 * Tags for an AisPacket. Encoded as comment blocks.
 */
@NotThreadSafe
public class AisPacketTags implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The constant SOURCE_ID_KEY.
     */
    public static final String SOURCE_ID_KEY = "si";
    /**
     * The constant SOURCE_BS_KEY.
     */
    public static final String SOURCE_BS_KEY = "sb";
    /**
     * The constant SOURCE_COUNTRY_KEY.
     */
    public static final String SOURCE_COUNTRY_KEY = "sc";
    /**
     * The constant SOURCE_TYPE_KEY.
     */
    public static final String SOURCE_TYPE_KEY = "st";

    /**
     * Timestamp (comment block key: 'c', value: seconds since 1970)
     */
    private Date timestamp;
    /**
     * Source identifier (comment block key: 'si')
     */
    private String sourceId;
    /**
     * Source base station MMSI (comment block key: 'sb')
     */
    private Integer sourceBs;
    /**
     * Source country in ISO 3166 three letter code (comment block key: 'sc')
     */
    private Country sourceCountry;
    /**
     * Source type (comment block key: 'st', value: SAT | LIVE)
     */
    private SourceType sourceType;

    /**
     * Instantiates a new Ais packet tags.
     */
    public AisPacketTags() {

    }

    /**
     * Copy constructor
     *
     * @param t the t
     */
    public AisPacketTags(AisPacketTags t) {
        if (t.timestamp != null) {
            this.timestamp = (Date) t.timestamp.clone();
        }
        this.sourceId = t.sourceId;
        this.sourceBs = t.sourceBs;
        this.sourceCountry = t.sourceCountry;
    }

    /**
     * Determine if any tag is non null
     *
     * @return boolean boolean
     */
    public boolean isEmpty() {
        return timestamp == null && sourceId == null && sourceBs == null && sourceCountry == null && sourceType == null;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets source id.
     *
     * @return the source id
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets source id.
     *
     * @param sourceId the source id
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Gets source bs.
     *
     * @return the source bs
     */
    public Integer getSourceBs() {
        return sourceBs;
    }

    /**
     * Sets source bs.
     *
     * @param sourceBs the source bs
     */
    public void setSourceBs(Integer sourceBs) {
        this.sourceBs = sourceBs;
    }

    /**
     * Gets source country.
     *
     * @return the source country
     */
    public Country getSourceCountry() {
        return sourceCountry;
    }

    /**
     * Sets source country.
     *
     * @param sourceCountry the source country
     */
    public void setSourceCountry(Country sourceCountry) {
        this.sourceCountry = sourceCountry;
    }

    /**
     * Sets source type.
     *
     * @param sourceType the source type
     */
    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * Gets source type.
     *
     * @return the source type
     */
    public SourceType getSourceType() {
        return sourceType;
    }

    /**
     * Make comment block with tags
     *
     * @return comment block
     */
    public CommentBlock getCommentBlock() {
        return getCommentBlock(new CommentBlock());
    }

    /**
     * Supplement given comment block with tags (overriding)
     *
     * @param cb the cb
     * @return comment block
     */
    public CommentBlock getCommentBlock(CommentBlock cb) {
        if (timestamp != null) {
            cb.addTimestamp(timestamp);
        }
        if (sourceId != null) {
            cb.addString(SOURCE_ID_KEY, sourceId);
        }
        if (sourceBs != null) {
            cb.addInt(SOURCE_BS_KEY, sourceBs);
        }
        if (sourceCountry != null) {
            cb.addString(SOURCE_COUNTRY_KEY, sourceCountry.getThreeLetter());
        }
        if (sourceType != null) {
            cb.addString(SOURCE_TYPE_KEY, sourceType.encode());
        }
        return cb;
    }

    /**
     * Supplement given comment block with tags (not overriding)
     *
     * @param cb the cb
     * @return comment block preserve
     */
    public CommentBlock getCommentBlockPreserve(CommentBlock cb) {
        if (timestamp != null && !cb.contains("c")) {
            cb.addTimestamp(timestamp);
        }
        if (sourceId != null && !cb.contains(SOURCE_ID_KEY)) {
            cb.addString(SOURCE_ID_KEY, sourceId);
        }
        if (sourceBs != null && !cb.contains(SOURCE_BS_KEY)) {
            cb.addInt(SOURCE_BS_KEY, sourceBs);
        }
        if (sourceCountry != null && !cb.contains(SOURCE_COUNTRY_KEY)) {
            cb.addString(SOURCE_COUNTRY_KEY, sourceCountry.getThreeLetter());
        }
        if (sourceType != null && !cb.contains(SOURCE_TYPE_KEY)) {
            cb.addString(SOURCE_TYPE_KEY, sourceType.encode());
        }
        return cb;
    }

    /**
     * Get new tagging with tags in proposed tagging not already in the current tag
     *
     * @param proposed the proposed
     * @return ais packet tags
     */
    public AisPacketTags mergeMissing(AisPacketTags proposed) {
        AisPacketTags addedTagging = new AisPacketTags();
        if (getSourceId() == null && proposed.getSourceId() != null) {
            addedTagging.setSourceId(proposed.getSourceId());
        }
        if (getSourceBs() == null && proposed.getSourceBs() != null) {
            addedTagging.setSourceBs(proposed.getSourceBs());
        }
        if (getSourceCountry() == null && proposed.getSourceCountry() != null) {
            addedTagging.setSourceCountry(proposed.getSourceCountry());
        }
        if (getSourceType() == null && proposed.getSourceType() != null) {
            addedTagging.setSourceType(proposed.getSourceType());
        }
        return addedTagging;
    }

    /**
     * Determine if given tagging match this tagging
     *
     * @param tagging the tagging
     * @return boolean boolean
     */
    public boolean filterMatch(AisPacketTags tagging) {
        if (sourceId != null && (tagging.getSourceId() == null || !tagging.getSourceId().equals(sourceId))) {
            return false;
        }
        if (sourceBs != null && (tagging.getSourceBs() == null || tagging.getSourceBs().intValue() != sourceBs)) {
            return false;
        }
        if (sourceCountry != null && (tagging.getSourceCountry() == null || !tagging.getSourceCountry().equals(sourceCountry))) {
            return false;
        }
        // Default tagging is TERRESTRIAL
        if (sourceType != null) {
            SourceType taggingSourceType = tagging.getSourceType() != null ? tagging.getSourceType() : SourceType.TERRESTRIAL;
            if (taggingSourceType != sourceType) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parse tags from Vdm. Uses comment block with first priority and fall back to proprietary tags.
     *
     * @param vdm the vdm
     * @return tagging instance
     */
    static AisPacketTags parse(Vdm vdm) {
        AisPacketTags tags = new AisPacketTags();
        // Get timestamp
        tags.setTimestamp(vdm != null ? vdm.getTimestamp() : null);
        // Get comment block
        CommentBlock cb = vdm != null ? vdm.getCommentBlock() : null;
        // Get from comment block
        if (cb != null) {
            tags.setSourceId(cb.getString(SOURCE_ID_KEY));
            tags.setSourceBs(cb.getInt(SOURCE_BS_KEY));
            String cc = cb.getString(SOURCE_COUNTRY_KEY);
            if (cc != null) {
                tags.setSourceCountry(Country.getByCode(cc));
            }
            tags.setSourceType(SourceType.fromString(cb.getString(SOURCE_TYPE_KEY)));
        }

        // Go through proprietary tags to set missing fields
        if (vdm == null || vdm.getTags() == null) {
            return tags;
        }
        for (IProprietaryTag tag : vdm.getTags()) {
            if (tag instanceof IProprietarySourceTag) {
                IProprietarySourceTag sourceTag = (IProprietarySourceTag) tag;
                if (tags.getSourceBs() == null) {
                    tags.setSourceBs(sourceTag.getBaseMmsi());
                }
                if (tags.getSourceCountry() == null) {
                    tags.setSourceCountry(sourceTag.getCountry());
                }
            }
        }

        return tags;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AisPacketTagging [timestamp=");
        builder.append(timestamp);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", sourceBs=");
        builder.append(sourceBs);
        builder.append(", sourceCountry=");
        builder.append(sourceCountry);
        builder.append(", sourceType=");
        builder.append(sourceType);
        builder.append("]");
        return builder.toString();
    }


    /**
     * The enum Source type.
     */
    public enum SourceType {
        /**
         * Terrestrial source type.
         */
        TERRESTRIAL,
        /**
         * Satellite source type.
         */
        SATELLITE;

        /**
         * From string source type.
         *
         * @param st the st
         * @return the source type
         */
        public static SourceType fromString(String st) {
            if (st == null) {
                return null;
            }
            if (st.equalsIgnoreCase("LIVE")) {
                return TERRESTRIAL;
            } else if (st.equalsIgnoreCase("SAT")) {
                return SATELLITE;
            }
            throw new IllegalArgumentException("Unknow source type: " + st);
        }

        /**
         * Encode string.
         *
         * @return the string
         */
        public String encode() {
            return this == TERRESTRIAL ? "LIVE" : "SAT";
        }
    }
}
