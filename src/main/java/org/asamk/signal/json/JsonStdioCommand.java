package org.asamk.signal.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonStdioCommand {

    @JsonProperty
    public String commandName;
    @JsonProperty
    public String recipient;
    @JsonProperty
    public String content;
    @JsonProperty
    public JsonNode details;
}
