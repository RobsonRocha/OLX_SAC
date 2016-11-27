package br.com.olx.challenge.test.mock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.primefaces.context.ApplicationContext;
import org.primefaces.util.AjaxRequestBuilder;
import org.primefaces.util.CSVBuilder;
import org.primefaces.util.StringEncrypter;
import org.primefaces.util.WidgetBuilder;


public class RequestContext extends org.primefaces.context.RequestContext {

	@Override
	public boolean isAjaxRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addCallbackParam(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getCallbackParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getScriptsToExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(String script) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollTo(String clientId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Collection<String> collection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(String expressions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(Collection<String> expressions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public WidgetBuilder getWidgetBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxRequestBuilder getAjaxRequestBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSVBuilder getCSVBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Object, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openDialog(String outcome) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openDialog(String outcome, Map<String, Object> options,
			Map<String, List<String>> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeDialog(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMessageInDialog(FacesMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ApplicationContext getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringEncrypter getEncrypter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIgnoreAutoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRTL() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
