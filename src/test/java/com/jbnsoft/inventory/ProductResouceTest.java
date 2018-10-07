package com.jbnsoft.inventory;


import com.jbnsoft.inventory.resource.customer.CustomerResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductResouceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerResource customerResource;

    @Before
    public  void setUp()  {
        mockMvc = MockMvcBuilders.standaloneSetup(customerResource).build();
    }

    @Test
    public  void testFindById() throws  Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/findById" )
            ).andExpect(MockMvcResultMatchers.status().isOk());

    }
}
