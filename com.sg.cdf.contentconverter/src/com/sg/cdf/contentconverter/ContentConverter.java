package com.sg.cdf.contentconverter;

import java.io.File;
import java.util.Properties;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.document.DefaultDocumentFormatRegistry;
import org.artofsolving.jodconverter.document.MediaType;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.sg.cdf.core.CDF;

public class ContentConverter implements BundleActivator {

	private static BundleContext context;
	
	private static OfficeManager officeManager;
	
	private static DefaultDocumentFormatRegistry formatRegistry;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		ContentConverter.context = bundleContext;
		bundleContext.addBundleListener(new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent event) {
				if (event.getBundle().getSymbolicName()
						.equals("com.sg.cdf.core")) {
					loadService();
				}
			}
		});
	}
	
	protected void loadService() {
		Properties props = CDF.getDefault().getProperties();
		String sSofficePort = props.getProperty("office.port"); //$NON-NLS-1$
		String sSofficeHome = props.getProperty("office.home"); //$NON-NLS-1$
		String sTaskExecutionTimeout = props
				.getProperty("office.task.executiontimeout"); //$NON-NLS-1$
		String sTaskQueueTimeout = props.getProperty("office.task.queuetimeout"); //$NON-NLS-1$
		String sMaxTasksPerProcess = props.getProperty("office.task.maxperprocess"); //$NON-NLS-1$
		
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		if (sSofficeHome != null && sSofficeHome.length() > 0) {
			configuration.setOfficeHome(sSofficeHome);// ����OpenOffice.org��װĿ¼
		}

		try {
			String[] sPorts = sSofficePort.split(",");
			int[] portNumbers = new int[sPorts.length];
			for (int i = 0; i < sPorts.length; i++) {
				portNumbers[i] = Integer.parseInt(sPorts[i]);
			}
			configuration.setPortNumbers(portNumbers); // ����ת���˿�
			configuration
					.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);
		} catch (Exception e) {
		}
		try {
			long taskExecutionTimeout = Long.parseLong(sTaskExecutionTimeout);
			configuration.setTaskExecutionTimeout(taskExecutionTimeout);// ��������ִ�г�ʱ
		} catch (Exception e) {
		}

		try {
			long taskQueueTimeout = Long.parseLong(sTaskQueueTimeout);
			configuration.setTaskQueueTimeout(taskQueueTimeout);// ����������г�ʱ
		} catch (Exception e) {
		}

		try {
			int maxTasksPerProcess = Integer.parseInt(sMaxTasksPerProcess);
			configuration.setMaxTasksPerProcess(maxTasksPerProcess);
		} catch (Exception e) {
		}

		officeManager = configuration.buildOfficeManager();

		formatRegistry = new DefaultDocumentFormatRegistry();

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				officeManager.start(); // ��������
			}

		});
		t.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		ContentConverter.context = null;
		officeManager.stop();
	}
	
	/**
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @param mediaType
	 *            {@link MediaType}
	 * @throws Exception
	 */
	public static void convert(File inputFile, File outputFile, String mediaType)
			throws Exception {
		OfficeDocumentConverter odc = new OfficeDocumentConverter(
				officeManager, formatRegistry);
		odc.convert(inputFile, outputFile,
				formatRegistry.getFormatByExtension(mediaType));
	}

}