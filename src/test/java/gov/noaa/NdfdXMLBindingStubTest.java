package gov.noaa;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;

import java.io.StringReader;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for NOAA SOAP Service.
 */
class NdfdXMLBindingStubTest {

    /**
     * Test Getting latitude and longitude list of a zip code.
     *
     * @throws ServiceException the service exception
     * @throws RemoteException  the remote exception
     * @throws JAXBException    the jaxb exception
     */
    @Test
    void testGetLatLonListbyZipCode() throws ServiceException, RemoteException, JAXBException {

        NdfdXMLBindingStub bindingStub = (NdfdXMLBindingStub) new NdfdXMLLocator().getndfdXMLPort();

        String postalCode = "53718";
        String expectedLatLon = "43.0798,-89.3875";
        String response =  bindingStub.latLonListZipCode(postalCode);

        // create JAXB context and initializing Marshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(DwmlType.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        // Instantiate a Java DwmlType object from the XML returned by the service
        DwmlType dwml = (DwmlType) jaxbUnmarshaller.unmarshal(new StringReader(response));

        assertEquals(expectedLatLon, dwml.getLatLonList());
    }
}