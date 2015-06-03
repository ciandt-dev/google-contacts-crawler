package com.ciandt.gcc.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.ciandt.gcc.ReaderPropertiesDomains;

public class ReaderPropertiesDomainsTest {
    
    @Test
    public void getPropertiesTest() throws IOException {
        
        ReaderPropertiesDomains d = new ReaderPropertiesDomains();
        Assert.assertNotNull(d.getProperties());
        
    }
}
