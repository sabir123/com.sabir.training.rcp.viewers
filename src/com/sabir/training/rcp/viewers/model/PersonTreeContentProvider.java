package com.sabir.training.rcp.viewers.model;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

public class PersonTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return ((List<Person>)inputElement).toArray();
		
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(((Person)parentElement).hasChildren())
		return	((Person)parentElement).getChildren().toArray();
		return null;
	}

	@Override
	public Object getParent(Object element) {
	return	((Person)element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		if(((Person)element).hasChildren())
		return true;
		
		return false;
	}

}
