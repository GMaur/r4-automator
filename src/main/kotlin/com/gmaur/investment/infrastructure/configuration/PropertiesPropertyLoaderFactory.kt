package com.gmaur.investment.infrastructure.configuration

import org.springframework.boot.env.PropertiesPropertySourceLoader
import org.springframework.core.io.support.DefaultPropertySourceFactory
import org.springframework.core.io.support.EncodedResource

open class PropertiesPropertyLoaderFactory : DefaultPropertySourceFactory() {
    override fun createPropertySource(name: String?, resource: EncodedResource): org.springframework.core.env.PropertySource<*> {
        return PropertiesPropertySourceLoader().load(resource.resource.filename, resource.resource, null)
    }
}