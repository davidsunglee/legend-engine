package org.finos.legend.engine.external.format.cnas.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finos.legend.engine.external.format.cnas.schema.generations.CNASGenerationInput;
import org.finos.legend.engine.external.format.cnas.schema.generations.CNASGenerationService;
import org.finos.legend.engine.language.pure.modelManager.ModelManager;
import org.finos.legend.engine.shared.core.ObjectMapperFactory;
import org.finos.legend.engine.shared.core.deployment.DeploymentMode;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Objects;

public class TestCNASGeneration
{
    private final ObjectMapper objectMapper = ObjectMapperFactory.getNewStandardObjectMapperWithPureProtocolExtensionSupports();

    @Test
    public void testCNASConfig() throws Exception
    {
        //String expected = "[{\"content\":\"{\\\"type\\\":\\\"record\\\",\\\"name\\\":\\\"A\\\",\\\"namespace\\\":\\\"meta.cnas.service.tests.model.latest\\\",\\\"fields\\\":[{\\\"name\\\":\\\"b\\\",\\\"type\\\":[\\\"null\\\",{\\\"type\\\":\\\"record\\\",\\\"name\\\":\\\"B\\\",\\\"namespace\\\":\\\"meta.cnas.service.tests.model.latest\\\",\\\"fields\\\":[{\\\"name\\\":\\\"i\\\",\\\"type\\\":\\\"long\\\"}]}],\\\"default\\\":null},{\\\"name\\\":\\\"e\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"items\\\":{\\\"type\\\":\\\"enum\\\",\\\"name\\\":\\\"E\\\",\\\"namespace\\\":\\\"meta.cnas.service.tests.model.latest\\\",\\\"symbols\\\":[\\\"E1\\\",\\\"E2\\\"]}}}]}\",\"fileName\":\"meta/cnas/service/tests/model/1_0_0/A.cnas\",\"format\":\"json\"}]";
        String expected = ""; // to fill out
        test("cnasInputWithNameSpaceConfig.json", expected);
    }

    @Test
    public void testCNAS() throws Exception
    {
        //String expected = "[{\"content\":\"{\\\"type\\\":\\\"record\\\",\\\"name\\\":\\\"A\\\",\\\"namespace\\\":\\\"meta.avro.service.tests.model.1_0_0\\\",\\\"fields\\\":[{\\\"name\\\":\\\"b\\\",\\\"type\\\":[\\\"null\\\",{\\\"type\\\":\\\"record\\\",\\\"name\\\":\\\"B\\\",\\\"namespace\\\":\\\"meta.avro.service.tests.model.1_0_0\\\",\\\"fields\\\":[{\\\"name\\\":\\\"i\\\",\\\"type\\\":\\\"long\\\"}]}],\\\"default\\\":null},{\\\"name\\\":\\\"e\\\",\\\"type\\\":{\\\"type\\\":\\\"array\\\",\\\"items\\\":{\\\"type\\\":\\\"enum\\\",\\\"name\\\":\\\"E\\\",\\\"namespace\\\":\\\"meta.avro.service.tests.model.1_0_0\\\",\\\"symbols\\\":[\\\"E1\\\",\\\"E2\\\"]}}}]}\",\"fileName\":\"meta/avro/service/tests/model/1_0_0/A.avro\",\"format\":\"json\"}]";
        String expected = ""; // to fill out
        test("cnasInputNoConfig.json", expected);
    }

    public void test(String directoryInput, String expected) throws Exception
    {
        InputStream stream;
        stream = Objects.requireNonNull(TestCNASGeneration.class.getResourceAsStream(directoryInput));
        String jsonInput = new java.util.Scanner(stream).useDelimiter("\\A").next();
        CNASGenerationInput request = objectMapper.readValue(jsonInput, CNASGenerationInput.class);
        ModelManager modelManager = new ModelManager(DeploymentMode.TEST);
        CNASGenerationService executor = new CNASGenerationService(modelManager);
        Response response = executor.generateCNAS(request, null);
        String returnedContext = response.getEntity().toString();
        Assert.assertEquals(expected, returnedContext);
        stream.close();
    }
}

// to do: add resource files for tests - simpleFileGeneration.json, cnasInputNoConfig.json, cnasInputWithNameSpaceConfig.json, etc