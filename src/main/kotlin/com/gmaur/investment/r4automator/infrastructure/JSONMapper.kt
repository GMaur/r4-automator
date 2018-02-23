package com.gmaur.investment.r4automator.infrastructure

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gmaur.investment.r4automator.app.FundDefinitionDTO
import com.gmaur.investment.r4automator.app.FundOperationRequest
import com.gmaur.investment.r4automator.app.OperationRequest
import com.gmaur.investment.r4automator.infrastructure.alien.robotadvisor.AmountDTO
import java.io.IOException

class JSONMapper {
    companion object {
        fun aNew(): ObjectMapper {
            val mapper = jacksonObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            val module = SimpleModule()
            module.addDeserializer(OperationRequest::class.java, OperationRequestDeserializer())
            mapper.registerModule(module)
            return mapper
        }
    }
}

class OperationRequestDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<OperationRequest>(vc) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): OperationRequest {
        val node = jp.codec.readTree<TreeNode>(jp)
        val result = when (string(node.get("asset"), "type")) {
            "fund" -> {
                FundOperationRequest(
                        type = string(node, "type"),
                        asset = FundDefinitionDTO(string(node.get("asset"), "isin")),
                        amount = AmountDTO.EUR(string(node.get("amount"), "value")))
            }
            else -> {
                throw IllegalArgumentException("type not recognized in: " + node.toString())
            }
        }
        return result
    }

    private fun string(node: TreeNode, key: String) = (node.get(key) as TextNode).asText()
}