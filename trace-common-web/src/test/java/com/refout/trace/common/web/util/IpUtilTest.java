package com.refout.trace.common.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class IpUtilTest {

    @Test
    public void testGetIpAddr() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("x-forwarded-for", "192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100");
        request.addHeader("Proxy-Client-IP", "192.168.1.120");
        request.addHeader("X-Forwarded-For", "192.168.1.130");
        request.addHeader("WL-Proxy-Client-IP", "192.168.1.140");
        request.addHeader("X-Real-IP", "192.168.1.150");
        request.setRemoteAddr("192.168.1.160");
        String ipAddr = IpUtil.getIpAddr(request);
        Assertions.assertEquals("192.168.1.110", ipAddr);
    }

    @Test
    public void testInternalIp() {
        boolean isInternal = IpUtil.internalIp("192.168.1.1");
        Assertions.assertTrue(isInternal);
    }

    @Test
    public void testTextToNumericFormatV4() {
        byte[] bytes = IpUtil.textToNumericFormatV4("192.168.1.1");
        Assertions.assertNotNull(bytes);
        Assertions.assertEquals(4, bytes.length);
    }

    @Test
    public void testGetHostIp() {
        String hostIp = IpUtil.getHostIp();
        Assertions.assertNotNull(hostIp);
        // Add additional assertions if necessary
    }

    @Test
    public void testGetHostName() {
        String hostName = IpUtil.getHostName();
        Assertions.assertNotNull(hostName);
        // Add additional assertions if necessary
    }

    @Test
    public void testGetMultistageReverseProxyIp() {
        String ip = IpUtil.getMultistageReverseProxyIp("192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100");
        Assertions.assertEquals("192.168.1.110", ip);
        // Add additional assertions if necessary
    }

    @Test
    public void testIsUnknown() {
        boolean isUnknown = IpUtil.isUnknown("unknown");
        Assertions.assertTrue(isUnknown);
        // Add additional assertions if necessary
    }

    @Test
    public void testIsIP() {
        boolean isIp = IpUtil.isIP("192.168.1.1");
        Assertions.assertTrue(isIp);
        // Add additional assertions if necessary
    }

    @Test
    public void testIsIpWildCard() {
        boolean isIpWildCard = IpUtil.isIpWildCard("192.168.1.*");
        Assertions.assertTrue(isIpWildCard);
        // Add additional assertions if necessary
    }

    @Test
    public void testIpIsInWildCardNoCheck() {
        boolean isInWildCard = IpUtil.ipIsInWildCardNoCheck("192.168.1.*", "192.168.1.10");
        Assertions.assertTrue(isInWildCard);
        // Add additional assertions if necessary
    }

    @Test
    public void testIsIPSegment() {
        boolean isIpSegment = IpUtil.isIPSegment("192.168.1.1-192.168.1.100");
        Assertions.assertTrue(isIpSegment);
        // Add additional assertions if necessary
    }

    @Test
    public void testIpIsInNetNoCheck() {
        boolean isInNet = IpUtil.ipIsInNetNoCheck("192.168.1.1-192.168.1.100", "192.168.1.50");
        Assertions.assertTrue(isInNet);
        // Add additional assertions if necessary
    }

    @Test
    public void testIsMatchedIp() {
        boolean isMatchedIp = IpUtil.isMatchedIp("192.168.1.1;192.168.1.*", "192.168.1.10");
        Assertions.assertTrue(isMatchedIp);
        // Add additional assertions if necessary
    }

}