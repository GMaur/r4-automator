package com.gmaur.investment.r4automator.infrastructure.configuration

import org.springframework.boot.env.PropertiesPropertySourceLoader
import org.springframework.core.io.support.DefaultPropertySourceFactory
import org.springframework.core.io.support.EncodedResource

// Source: https://stackoverflow.com/questions/45880494/configurationproperties-loading-list-from-yml#45882088

open class PropertiesPropertyLoaderFactory : DefaultPropertySourceFactory() {
    override fun createPropertySource(name: String?, resource: EncodedResource): org.springframework.core.env.PropertySource<*> {
        return PropertiesPropertySourceLoader().load(resource.resource.filename, resource.resource, null)
    }
}