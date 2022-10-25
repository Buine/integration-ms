package com.github.tesis.integrationms.constants

object Routes {
    const val HEALTH_CHECK = "/health-check"

    object Integration {
        const val V1 = "/v1"
        const val INTEGRATION = "/integration"
        const val GET_BY_CODE = "/{integration_code}"
        const val GET_SCHEMA = "$GET_BY_CODE/schema"
    }
}
