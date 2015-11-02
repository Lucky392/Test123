/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import rs.htec.cms.cms_bulima.constants.MethodConstants;
import rs.htec.cms.cms_bulima.domain.QuestionOfTheDayPrize;
import rs.htec.cms.cms_bulima.exception.DataNotFoundException;
import rs.htec.cms.cms_bulima.helper.GetObject;
import rs.htec.cms.cms_bulima.helper.RestHelperClass;
import rs.htec.cms.cms_bulima.helper.Validator;
import rs.htec.cms.cms_bulima.service.QuestionOfTheDayPrizeRESTEndpoint;

/**
 *
 * @author marko
 */
public class QuestionOfTheDayPrizeRESTEndpointTest {

    QuestionOfTheDayPrizeRESTEndpoint qotdp;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public QuestionOfTheDayPrizeRESTEndpointTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
//        this.qotdp = new QuestionOfTheDayPrizeRESTEndpoint();
//        this.qotdp.em = mock(EntityManager.class);
    }

    @After
    public void tearDown() {
    }

    void mockMethods() {
//        when(this.qotdp.helper.getEntityManager()).thenReturn(this.qotdp.em);
//        Mockito.doNothing().when(this.qotdp.helper).checkUserAndPrivileges((EntityManager) Matchers.anyObject(), Matchers.anyInt(), any(MethodConstants.class), anyString());
//        Query mockedQuery = mock(Query.class);
//        Query mockedQuery1 = mock(Query.class);
//        List<QuestionOfTheDayPrize> list = new ArrayList<>();
//        list.add(new QuestionOfTheDayPrize());
//        when(mockedQuery.getResultList()).thenReturn(list);
//        when(mockedQuery.getSingleResult()).thenReturn(new QuestionOfTheDayPrize());
//        when(this.qotdp.em.createNamedQuery("QuestionOfTheDayPrize.findAll")).thenReturn(mockedQuery);
//        when(this.qotdp.em.createNamedQuery("QuestionOfTheDayPrize.findById")).thenReturn(mockedQuery);
//        when(mockedQuery.setFirstResult(Matchers.anyInt())).thenReturn(mockedQuery);
//        when(mockedQuery.setMaxResults(Matchers.anyInt())).thenReturn(mockedQuery);
//        when(mockedQuery.setParameter(anyString(), any(Long.class))).thenReturn(mockedQuery);
//        when(this.qotdp.em.createQuery("Select COUNT(ip) From QuestionOfTheDayPrize ip")).thenReturn(mockedQuery1);
//        when(mockedQuery1.getSingleResult()).thenReturn(1l);
    }

    void mockMethods1() {
////        when(this.qotdp.helper.getEntityManager()).thenReturn(this.qotdp.em);
////        Mockito.doNothing().when(this.qotdp.helper).checkUserAndPrivileges((EntityManager) Matchers.anyObject(), Matchers.anyInt(), any(MethodConstants.class), anyString());
//        Query mockedQuery = mock(Query.class);
//        Query mockedQuery1 = mock(Query.class);
//        List<QuestionOfTheDayPrize> list = new ArrayList<>();
////        list.add(new QuestionOfTheDayPrize());
//        when(mockedQuery.getResultList()).thenReturn(list);
//        when(mockedQuery.getSingleResult()).thenReturn(new QuestionOfTheDayPrize());
//        when(this.qotdp.em.createNamedQuery("QuestionOfTheDayPrize.findAll")).thenReturn(mockedQuery);
//        when(this.qotdp.em.createNamedQuery("QuestionOfTheDayPrize.findById")).thenReturn(mockedQuery);
//        when(mockedQuery.setFirstResult(Matchers.anyInt())).thenReturn(mockedQuery);
//        when(mockedQuery.setMaxResults(Matchers.anyInt())).thenReturn(mockedQuery);
//        when(mockedQuery.setParameter(anyString(), any(Long.class))).thenReturn(mockedQuery);
//        when(this.qotdp.em.createQuery("Select COUNT(ip) From QuestionOfTheDayPrize ip")).thenReturn(mockedQuery1);
//        when(mockedQuery1.getSingleResult()).thenReturn(1l);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetPrizeSuccess() {
//        mockMethods();
//        final Response r = this.qotdp.getPrize("test", 1, 1);
//        assertNotNull(r);
//        assertEquals(r.getStatus(), 200);
//        assertEquals(r.getEntity().getClass(), GetObject.class);
//        assertEquals(((GetObject) r.getEntity()).getData().getClass(), new ArrayList<QuestionOfTheDayPrize>().getClass());
//        assertEquals(((GetObject) r.getEntity()).getData().size(), 1);
//        assertEquals(((GetObject) r.getEntity()).getData().get(0).getClass(), QuestionOfTheDayPrize.class);
//        assertEquals(((GetObject) r.getEntity()).getCount(), 1l);
    }

    @Test
    public void testGetPrizeByIdSuccess() {
//        mockMethods();
//        try {
//            final Response r = this.qotdp.getPrizeById("test", 1);
//        } catch (DataNotFoundException e) {
//
//        }
    }
//         
//         assertNotNull(r);
//         assertEquals(r.getStatus(), 200);
//         assertEquals(r.getEntity().getClass(), QuestionOfTheDayPrize.class);
//    }
}
