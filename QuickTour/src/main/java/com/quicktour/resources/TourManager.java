package com.quicktour.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.quicktour.model.NewPackage;
import com.quicktour.model.PackageInfo;
import com.quicktour.model.UpdatePackage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Path("/tour")
@Api("TourManager")
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public class TourManager {
	protected Map<String, PackageInfo> packages;

	public TourManager() {
		packages = new ConcurrentHashMap<>();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation( value = "Create new Package")
	@Path("/createpackage")
	public StreamingOutput newPackage(InputStream in) {
		PackageInfoStreamingOutput streamingOutput = null;
		PackageInfo packageInfo = null;
		NewPackage nPackage = null;
		try {
			nPackage = readNewPackage(in);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		packageInfo = new PackageInfo();
		packageInfo.setPackageName(nPackage.getPackageName());
		packageInfo.setAmount(7000);
		packageInfo.setComments("7daysSingspoor");
		packageInfo.setOrderNnum(UUID.randomUUID().toString());
		packageInfo.setStatus("process");

		packages.put(packageInfo.getOrderNum(), packageInfo);
		streamingOutput = new PackageInfoStreamingOutput(packageInfo);

		return streamingOutput;
	}

	@GET
	@ApiOperation(value="Get package info by passing Order Number")
	@Path("/getPackageInfo")
	public PackageInfo getpackageDetails(@QueryParam("order-no") String orderNum) {
		PackageInfo packageInfo = null;
		if (packages.containsKey(orderNum)) {
			packageInfo = packages.get(orderNum);
			
		}
		return packageInfo;

	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@ApiOperation(value="Update existing package")
	@Path("/updatePackage")
	public StreamingOutput updatePackage(InputStream in) {
		UpdatePackage uPackage = null;
		PackageInfo packageInfo = null;
		PackageInfoStreamingOutput piOutput = null;
		try {
			uPackage = readUpdatePackage(in);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		packageInfo = new PackageInfo();
		packageInfo.setPackageName(uPackage.getNewPackageName());
		packageInfo.setAmount(5000f);
		packageInfo.setComments("changed successfully");
		packageInfo.setOrderNnum(UUID.randomUUID().toString());
		packageInfo.setStatus("process");
		packages.put(packageInfo.getOrderNum(), packageInfo);
		piOutput = new PackageInfoStreamingOutput(packageInfo);

		return piOutput;
	}

	private final class PackageInfoStreamingOutput implements StreamingOutput {
		private PackageInfo packageInfo;

		public PackageInfoStreamingOutput(PackageInfo packageInfo) {
			this.packageInfo = packageInfo;
		}

		@Override
		public void write(OutputStream os) throws IOException, WebApplicationException {
			StringBuffer buffer = null;
			buffer = new StringBuffer();
			buffer.append("<package-info>")
			.append("<package-name>").append(packageInfo.getPackageName()).append("</package-name>")
			.append("<amount>").append(packageInfo.getAmount()).append("</amount>")
			.append("<comments>").append(packageInfo.getComments()).append("</comments>")
			.append("<order-no>").append(packageInfo.getOrderNum()).append("</order-no>")
			.append("<status>").append(packageInfo.getStatus()).append("</status>")
			.append("</package-info>");

			os.write(buffer.toString().getBytes());
			os.close();
		}

	}

	private NewPackage readNewPackage(InputStream is) throws ParserConfigurationException, SAXException, IOException {
		NewPackage nPackage = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		Node root = null;
		NodeList children = null;
		Node child = null;

		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(is);
			nPackage = new NewPackage();
			root = doc.getFirstChild();
			children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				child = children.item(i);
				if (child.getNodeName().equals("package-name")) {
					nPackage.setPackageName(child.getTextContent());
				} else if (child.getNodeName().equals("person")) {
					nPackage.setPerson(child.getTextContent());
				} else if (child.getNodeName().equals("mobile")) {
					nPackage.setMobile(child.getTextContent());
				} else if (child.getNodeName().equals("email-address")) {
					nPackage.setEmailAddress(child.getTextContent());
				} 
			}
		
		return nPackage;
	}

	private UpdatePackage readUpdatePackage(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
		UpdatePackage uPackage = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		Node root = null;
		NodeList children = null;
		Node child = null;

		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(is);

		if (doc != null) {
			uPackage = new UpdatePackage();
			root = doc.getFirstChild();
			children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				child = children.item(i);
				if (child.getNodeName().equals("new-package-name")) {
					uPackage.setNewPackageName(child.getTextContent());
				} else if (child.getNodeName().equals("comments")) {
					uPackage.setComments(child.getTextContent());
				}
			}
		}
		return uPackage;
	}

	
}