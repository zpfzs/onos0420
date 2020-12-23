/*
 * Copyright 2020-present Open Networking Foundation
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
package org.onosproject.kubevirtnode.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * An utility that used in KubeVirt node app.
 */
public final class KubevirtNodeUtil {

    private static final Logger log = LoggerFactory.getLogger(KubevirtNodeUtil.class);

    private static final String COLON_SLASH = "://";
    private static final String COLON = ":";

    private static final int HEX_LENGTH = 16;
    private static final String OF_PREFIX = "of:";
    private static final String ZERO = "0";

    /**
     * Prevents object installation from external.
     */
    private KubevirtNodeUtil() {
    }

    /**
     * Generates a DPID (of:0000000000000001) from an index value.
     *
     * @param index index value
     * @return generated DPID
     */
    public static String genDpid(long index) {
        if (index < 0) {
            return null;
        }

        String hexStr = Long.toHexString(index);

        StringBuilder zeroPadding = new StringBuilder();
        for (int i = 0; i < HEX_LENGTH - hexStr.length(); i++) {
            zeroPadding.append(ZERO);
        }

        return OF_PREFIX + zeroPadding.toString() + hexStr;
    }

    /**
     * Generates string format based on the given string length list.
     *
     * @param stringLengths a list of string lengths
     * @return string format (e.g., %-28s%-15s%-24s%-20s%-15s)
     */
    public static String genFormatString(List<Integer> stringLengths) {
        StringBuilder fsb = new StringBuilder();
        stringLengths.forEach(length -> {
            fsb.append("%-");
            fsb.append(length);
            fsb.append("s");
        });
        return fsb.toString();
    }

    /**
     * Prints out the JSON string in pretty format.
     *
     * @param mapper        Object mapper
     * @param jsonString    JSON string
     * @return pretty formatted JSON string
     */
    public static String prettyJson(ObjectMapper mapper, String jsonString) {
        try {
            Object jsonObject = mapper.readValue(jsonString, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (IOException e) {
            log.debug("Json string parsing exception caused by {}", e);
        }
        return null;
    }
}