package com.github.karamelsoft.testing.data.driven.testing.camel.operations;

import com.github.karamelsoft.testing.data.driven.testing.camel.CamelTester;
import com.github.karamelsoft.testing.data.driven.testing.core.StringTester;
import com.github.karamelsoft.testing.data.driven.testing.core.TestFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by frederic on 02/05/15.
 */
public class SaveMockEndpointTest extends CamelTestSupport {

    public static final String SOURCE = "direct:source";

    @Produce(uri = SOURCE)
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:destination")
    private MockEndpoint destination;

    @Before
    public void before() throws Exception {
        context().addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(SOURCE)
                    .split().body(Collection.class)
                    .to("mock:destination");
            }
        });
    }

    @Test
    public void testConsume() throws IOException {

        TestFactory.createTest()
            .name("save-mock-endpoint")
            .scenario("consume")
            .begin()
                .value(Arrays.asList("one", "two"))
                .apply((List<String> col) -> producerTemplate.sendBody(SOURCE, col))
                .script(
                    CamelTester.<List<String>, String>saveMockEndpoint()
                        .mockEndpoint(destination)
                        .fileName("string.txt")
                        .save(StringTester.save())
                        .build())
                .compare("0-string.txt", StringTester.compare())
                .compare("1-string.txt", StringTester.compare())
                .end();
    }
}
