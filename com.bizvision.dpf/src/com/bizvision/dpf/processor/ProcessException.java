
package com.bizvision.dpf.processor;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://server.dpf.bizvision.com/")
public class ProcessException
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private com.bizvision.dpf.processor.Exception faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ProcessException(String message, com.bizvision.dpf.processor.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ProcessException(String message, com.bizvision.dpf.processor.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.bizvision.dpf.processor.Exception
     */
    public com.bizvision.dpf.processor.Exception getFaultInfo() {
        return faultInfo;
    }

}