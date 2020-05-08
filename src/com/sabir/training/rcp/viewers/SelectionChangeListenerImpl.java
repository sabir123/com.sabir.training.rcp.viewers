package com.sabir.training.rcp.viewers;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.sabir.training.rcp.viewers.model.Person;
import com.sabir.training.rcp.viewers.model.Skill;

public class SelectionChangeListenerImpl implements ISelectionChangedListener{

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
	System.out.println(event.getStructuredSelection().getFirstElement().getClass().getName());
	Person p =  (Person)(event.getStructuredSelection().getFirstElement());
	System.out.println("The selected person has below skills ");
	for(Skill s : p.getSkills()) {
		System.out.println(s.getName());
	}
		
	}

}
