/* AggregatorsTest.java - created on Oct 9, 2014, Copyright (c) 2011 The European Library, all rights reserved */
package org.theeuropeanlibrary.repox.rest.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.theeuropeanlibrary.repox.rest.configuration.JerseyConfigMocked;
import org.theeuropeanlibrary.repox.rest.pathOptions.AggregatorOptionListContainer;

import pt.utl.ist.dataProvider.Aggregator;
import pt.utl.ist.dataProvider.DefaultDataManager;
import pt.utl.ist.util.exceptions.AggregatorDoesNotExistException;

/**
 * Aggregators context path handling tests.
 * 
 * @author Simon Tzanakis (Simon.Tzanakis@theeuropeanlibrary.org)
 * @since Oct 9, 2014
 */
//@Ignore
public class AggregatorsResourceTest extends JerseyTest {

    DefaultDataManager dataManager;

    public AggregatorsResourceTest() throws Exception {
        super(new JerseyConfigMocked());
        dataManager = JerseyConfigMocked.getDataManager();
    }

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUpBeforeMethod() throws Exception {
        reset(dataManager);
    }

    /**
     * Test method for {@link org.theeuropeanlibrary.repox.rest.servlets.AggregatorsResource#getOptions()}.
     */
    @Test
    //    @Ignore
    public void testGetOptions() {
        int numberOfAvailableOptions = 1;
        WebTarget target = target("/" + AggregatorOptionListContainer.AGGREGATORS);

        //Check xml options working
        Response response = target.request(MediaType.APPLICATION_XML).options();
        assertEquals(200, response.getStatus());
        //Check json options working
        response = target.request(MediaType.APPLICATION_JSON).options();
        assertEquals(200, response.getStatus()); 
        AggregatorOptionListContainer aolc = response.readEntity(AggregatorOptionListContainer.class);
        //Check the number of options provided
        assertEquals(numberOfAvailableOptions, aolc.getOptionList().size());
    }

    /**
     * Test method for {@link org.theeuropeanlibrary.repox.rest.servlets.AggregatorsResource#getAggregator(java.lang.String)}.
     * @throws MalformedURLException 
     */
    @Test
    //    @Ignore
    public void testGetAggregator() throws MalformedURLException {
        String aggregatorId = "Austriar0";
        //Mocking
        when(dataManager.getAggregator(aggregatorId)).thenReturn(new Aggregator(aggregatorId, "testName", "namecode", new URL("http://something"), null));

        WebTarget target = target("/" + AggregatorOptionListContainer.AGGREGATORS + "/" + aggregatorId);
        //Check xml head working
        Response response = target.request(MediaType.APPLICATION_XML).head();
        assertEquals(200, response.getStatus());
        //Check json head working
        response = target.request(MediaType.APPLICATION_JSON).head();
        assertEquals(200, response.getStatus());
        //Check get xml working with 200 status
        response = target.request(MediaType.APPLICATION_XML).get();
        assertEquals(200, response.getStatus());
        Aggregator aggregator = response.readEntity(Aggregator.class);
        //Check get json working with 200 status
        response = target.request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, response.getStatus());
        aggregator = response.readEntity(Aggregator.class);
        assertEquals(aggregatorId, aggregator.getId());

        //Check Errors
        //Check get xml working with 404 status
        target = target("/" + AggregatorOptionListContainer.AGGREGATORS + "/" + "FakeAggregatorId");
        response = target.request(MediaType.APPLICATION_XML).get();
        assertEquals(404, response.getStatus());
        //Check get xml working with 404 status
        response = target.request(MediaType.APPLICATION_JSON).get();
        assertEquals(404, response.getStatus());
    }

    /**
     * TEMPORARY TEST METHOD
     * @throws JAXBException
     * @throws AggregatorDoesNotExistException 
     * @throws MalformedURLException 
     */
    @Test
    @Ignore
    public final void fastTesting() throws JAXBException, AggregatorDoesNotExistException, MalformedURLException {

    }

}