package com.bizvision.dpf.server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.bizvision.dpf.service.IPersistence;
import com.bizvision.dpf.service.IProcessor;

@WebService(endpointInterface = "com.bizvision.dpf.service.IProcessor")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Processor implements IProcessor {

	private static final int STATE_ONLINE = 1;

	private static final int STATE_OFFLINE = 0;

	private ProcessorConfig processorConfig;

	private String url;

	private TaskAllocator taskAllocator;

	private IPersistence persistence;

	private int state;

	@Override
	public Result execute(Task task) throws ProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessorPerformence getProcessorPerformence()
			throws ProcessException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUrl() {
		return url;
	}

	public ProcessorConfig getProcessorConfig() {
		return processorConfig;
	}

	public void setConfig(ProcessorConfig processorConfig) {
		this.processorConfig = processorConfig;
	}

	public void setTaskAllocator(TaskAllocator taskAllocator) {
		this.taskAllocator = taskAllocator;

	}

	public TaskAllocator getTaskAllocator() {
		return taskAllocator;
	}

	public void setPersistence(IPersistence persistence) {
		this.persistence = persistence;
	}

	public void online() {
		setState(STATE_ONLINE);
		update();
	}

	private void update() {
		ProcessorPersistable persistable = new ProcessorPersistable();
		persistable.setUrl(getUrl());
		persistable.setState(getState());
		persistable.setMaxThreadCount(getMaxThreadCount());
		ProcessorType[] type = getProcessorTypes();
		for (int i = 0; i < type.length; i++) {
			persistable.getProcessorTypes().add(type[i]);
		}
		persistable.setId(getId());
		persistable.setName(getName());
		persistence.updateProcessor(persistable);
	}

	public void offline() {
		setState(STATE_OFFLINE);
		update();
	}

	public int getMaxThreadCount() {
		return processorConfig.getMaxThreadCount();
	}

	public String getId() {
		return processorConfig.getId();
	}

	public String getName() {
		return processorConfig.getName();
	}

	public ProcessorType[] getProcessorTypes() {
		return processorConfig.getProcessorTypes();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
