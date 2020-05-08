package com.sabir.training.rcp.viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import com.sabir.training.rcp.viewers.model.Person;
import com.sabir.training.rcp.viewers.model.PersonTreeContentProvider;
import com.sabir.training.rcp.viewers.model.Skill;

public class View extends ViewPart {
	public static final String ID = "com.sabir.training.rcp.viewers.view";

	@Inject IWorkbench workbench;
	
	private TreeViewer treeViewer;
	
	private TableViewer tableViewer;
	
	private List<Person> persons;
	
	private class StringLabelProvider extends ColumnLabelProvider {
		
		// getText method is used from super class ColumnLabelProvider

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}
private class FirstNameLabelProvider extends ColumnLabelProvider {
		
		@Override
		public String getText(Object element) {
			return element == null ? "" : ((Person)element).getFirstName();
		}
	}

private class LastNameLabelProvider extends ColumnLabelProvider {
	
	@Override
	public String getText(Object element) {
		return element == null ? "" : ((Person)element).getLastName();
	}
}	
	
	
	@Override
	public void createPartControl(Composite parent) {
		
		persons = createDataModel();
		Composite viewerComposite = new Composite(parent, SWT.NONE);
		viewerComposite.setLayout(new GridLayout(2,false));
		
		treeViewer = new TreeViewer(viewerComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new PersonTreeContentProvider());
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		TreeViewerColumn firstNameColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		firstNameColumn.getColumn().setWidth(300);
		firstNameColumn.getColumn().setText("First Name");
		firstNameColumn.setLabelProvider(new FirstNameLabelProvider());
		
		TreeViewerColumn lastNameColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		lastNameColumn.getColumn().setWidth(100);
		lastNameColumn.getColumn().setText("Size");
		lastNameColumn.setLabelProvider(new LastNameLabelProvider());
		
		// Provide the input to the ContentProvider
		treeViewer.setInput(persons);
		treeViewer.refresh();
		
		getSite().setSelectionProvider(treeViewer);
		
		tableViewer = new TableViewer(viewerComposite, SWT.MULTI | SWT.H_SCROLL
	                | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, tableViewer);
        final Table table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true); 
        tableViewer.setContentProvider(new ArrayContentProvider());
        tableViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,2));
        tableViewer.setInput(persons.get(0).getSkills());
        tableViewer.refresh();
        
        treeViewer.addSelectionChangedListener(new SelectionChangeListenerImpl());
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
			Person p =  (Person)(event.getStructuredSelection().getFirstElement());
				tableViewer.setInput(p.getSkills());
				tableViewer.refresh();
				
			}
		});
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) {
        String[] titles = { "Skill name", "Proficiency"};
        int[] bounds = { 200 , 100};

        // First column is for the first name
        TableViewerColumn col1 = createTableViewerColumn(titles[0], bounds[0], 0);
        col1.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Skill skill = (Skill) element;
                return skill.getName();
            }
        });

        // Second column is for the last name
        TableViewerColumn col2 = createTableViewerColumn(titles[1], bounds[1], 1);
        col2.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	Skill skill = (Skill) element;
                return Integer.toString(skill.getProficiency());
            }
        });
    }
    
    
    private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
        final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer,
                SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);
        return viewerColumn;

    }

	
	public static List<Person> createDataModel() {
		List<Person> persons = new ArrayList<Person>();
		
	    Person p1 = new Person("Steve", "Large", 43);
		persons.add(p1);
		List<Skill> p1Skills = new ArrayList<Skill>();
		p1Skills.add(new Skill("C",3));
		p1Skills.add(new Skill("C++",3));
		p1Skills.add(new Skill("Java",5));
		p1.setSkills(p1Skills);
		
		
		
		Person p1Child = new Person("Isabella","Large",10);
		p1Child.setParent(p1);
		p1.addChildren(p1Child);
		List<Skill> p1ChildSkills = new ArrayList<Skill>();
		p1ChildSkills.add(new Skill("C",3));
		p1ChildSkills.add(new Skill("C++",3));
		p1ChildSkills.add(new Skill("Java",5));
		p1Child.setSkills(p1ChildSkills);
		
		
		
		
		Person p2 = new Person("John", "Volkens", 35);
		persons.add(p2);
		List<Skill> p2Skills = new ArrayList<Skill>();
		p2Skills.add(new Skill("C",3));
		p2Skills.add(new Skill("C++",3));
		p2Skills.add(new Skill("Python",3));
		p2.setSkills(p2Skills);
		
		
		Person p2Child = new Person("Abby","Volkens",4);
		p2Child.setParent(p2);
		p2.addChildren(p2Child);
		
		List<Skill> p2ChildSkills = new ArrayList<Skill>();
		p2ChildSkills.add(new Skill("Scala",3));
		p2ChildSkills.add(new Skill("GoLang",3));
		p2ChildSkills.add(new Skill("Clojure",5));
		p2Child.setSkills(p2ChildSkills);
		
		Person p3 = new Person("Kessler", "Parker", 29);
		persons.add(p3);
		
		List<Skill> p3Skills = new ArrayList<Skill>();
		p3Skills.add(new Skill("Ruby",3));
		p3Skills.add(new Skill("Perl",3));
		p3Skills.add(new Skill("Python",3));
		p3.setSkills(p3Skills);
		
		Person p3Child = new Person("Katie","Parker",2);
		p3Child.setParent(p3);
		p3.addChildren(p3Child);
		
		List<Skill> p3ChildSkills = new ArrayList<Skill>();
		p3ChildSkills.add(new Skill("Scala",3));
		p3ChildSkills.add(new Skill("GoLang",3));
		p3ChildSkills.add(new Skill("Clojure",5));
		p3Child.setSkills(p3ChildSkills);
		
//		Person p4 = new Person("Michael", "Dent", 40);
//		persons.add(p4);
//		
//		
//		
//		Person p4Child = new Person("Isabella","Large",10);
//		p4Child.setParent(p4);
//		p4.addChildren(p4Child);
//		
//		Person p5 = new Person("Jonathan", "Cox", 33);
//		persons.add(p5);
//		
//		Person p5Child = new Person("Isabella","Large",10);
//		p5Child.setParent(p5);
//		p5.addChildren(p5Child);
//		
//		Person p6 = new Person("Kris", "Klindworth", 33);
//		persons.add(p6);
//		
//		Person p6Child = new Person("Isabella","Large",10);
//		p6Child.setParent(p6);
//		p6.addChildren(p6Child);
//		
//		Person p7 = new Person("Beth", "Nepote", 44);
//		persons.add(p7);
//		
//		Person p7Child = new Person("Isabella","Large",10);
//		p7Child.setParent(p1);
//		p7.addChildren(p7Child);
//		
//		Person p8 = new Person("Sindhu", "Chitikela", 27);
//		persons.add(p8);
//		Person p8Child = new Person("Isabella","Large",10);
//		p8Child.setParent(p8);
//		p8.addChildren(p8Child);
//		
//		Person p9 = new Person("Mike", "Barns", 33);
//		persons.add(p9);
//		Person p9Child = new Person("Isabella","Large",10);
//		p9Child.setParent(p9);
//		p8.addChildren(p9Child);
//		
//		Person p10 = new Person("Sabir", "Pasha", 32);
//		persons.add(p10);
//		
//		Person p10Child = new Person("Yusra","Sabir",1);
//		p10Child.setParent(p10);
//		p10.addChildren(p10Child);
		
		return persons;
	}

	
	


	@Override
	public void setFocus() {
		treeViewer.getControl().setFocus();
	}
	
	private List<String> createInitialDataModel() {
		return Arrays.asList("One", "Two", "Three");
	}
}