package com.sj.wcc.util;

import java.io.InputStream;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sj.wcc.util.ServerConfig.ServerDef;

public class WccConfig {
	public static Logger logger = Logger.getLogger(WccConfig.class);
	
	public static final String ROOT_NAME = "servers";
	public static final String SERVER_NAME = "server";
	public static final String IMP_NAME = "imp";
	public static final String INSTANCE_NAME = "instance";
	public static final String ENABLE_NAME = "enabled";
	
	public static void init() throws Exception{
		logger.info("wcc load config[server.xml]...");
		InputStream is = WccConfig.class.getClassLoader().getResourceAsStream("server/server.xml");
		DocumentBuilderFactory buliderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = buliderFactory.newDocumentBuilder();
		Document document = builder.parse(is);
		Node root = document.getChildNodes().item(0);
		if(ROOT_NAME.equalsIgnoreCase(root.getNodeName())){
			NodeList nodeList = root.getChildNodes();
			for(int i = 0 ; i < nodeList.getLength() ; i++){
				Node server = nodeList.item(i);
				if(SERVER_NAME.equalsIgnoreCase(server.getNodeName())){
					NamedNodeMap map = server.getAttributes();
					String imp = map.getNamedItem(IMP_NAME).getNodeValue();
					String instance = map.getNamedItem(INSTANCE_NAME).getNodeValue();
					String enabled = map.getNamedItem(ENABLE_NAME).getNodeValue();
					ServerDef def = loadServerDef(imp,instance,enabled); 
					ServerConfig.scache.put(def.getImp(), def);
				}
			}
		}
	}
	
	private static ServerDef loadServerDef(String imp, String instance, String enabled){
		ServerDef def = new ServerDef();
		try {
			Class<?> clazz = Class.forName(imp);
			Method[] method = clazz.getMethods();
			for(Method m : method){
				MethodEx mex = new MethodEx();
				mex.method = m;
				mex.withThransaction = false;
				def.addMethod(mex);
			}
			def.setImp(imp);
			def.setInstance(instance);
			def.setEnadled(enabled);
			def.setLocker(false);
		} catch (ClassNotFoundException e) {
			logger.error("create serverdef error",e);
		}
		return def;
	}
	
	public static void main(String[] args) throws Exception {
		init();
	}
}
