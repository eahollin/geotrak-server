# geotrak-server project

GeoTrak (tm) Server is a component of the GeoTrak (tm) platform, Copyright 2020 Ed Hollingsworth.
It is freely licensed under the Open Source MIT License.

## Configuring the application

The properties "graphql.spqr.gui.targetEndpoint" and "graphql.spqr.gui.targetWsEndpoint" found in the src/main/resources/application.properties file  are used to configure the GraphQL endpoints with which the built-in GraphQL Playground gui will attempt to communicate.

You can use the "spring.kafka.consumer.bootstrap-servers", "kafka.bootstrap", and "kafka.topic" properties to customize your Kafka configuration, or include the environment variables KAFKA_BOOTSTRAP and KAFKA_TOPIC in your environment when launching the application, to override the default values.