package com.aldercape.internal.economics.persistence;

import java.io.File;

import org.junit.Before;

import com.aldercape.internal.economics.model.ClientRepository;
import com.aldercape.internal.economics.model.CollaboratorRepository;
import com.aldercape.internal.economics.model.Euro;
import com.aldercape.internal.economics.model.Project;
import com.aldercape.internal.economics.model.Rate;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class ProjectFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<Project> {

	private ProjectFileSystemRepository repository;
	private __FileSystemRepositories repositories;
	private RepositoryRegistry repositoryRegistry;
	private Project firstProject;
	private Project secondProject;

	@Before
	public void setUp() {
		__TestObjectMother objectMother = new __TestObjectMother();

		repositories.collaboratorRepository().add(objectMother.me());
		repositories.collaboratorRepository().add(objectMother.other());
		repositories.clientRepository().add(objectMother.myCompany());
		repositories.clientRepository().add(objectMother.otherCompany());

		firstProject = new Project("First project", objectMother.otherCompany());
		firstProject.addMember(objectMother.me(), Rate.daily(new Euro(200)));

		secondProject = new Project("Second project", objectMother.myCompany());
		secondProject.addMember(objectMother.other(), Rate.daily(new Euro(220)));

	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"name\":\"First project\",\"client\":2,\"members\":[{\"collaboratorId\":1,\"rate\":{\"amount\":{\"amount\":200}}}]}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"name\":\"Second project\",\"client\":1,\"members\":[{\"collaboratorId\":2,\"rate\":{\"amount\":{\"amount\":220}}}]}";
	}

	@Override
	protected Project getFirstEntry() {
		return firstProject;
	}

	@Override
	protected Project getSecondEntry() {
		return secondProject;
	}

	@Override
	protected ProjectFileSystemRepository getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(Project expected, Project actual) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createNewRepository(File newFile) {
		repositories = new __FileSystemRepositories(newFile.getParentFile());

		repositoryRegistry = new RepositoryRegistry();
		repositoryRegistry.setRepository(CollaboratorRepository.class, repositories.collaboratorRepository());
		repositoryRegistry.setRepository(ClientRepository.class, repositories.clientRepository());

		repository = new ProjectFileSystemRepository(newFile, repositoryRegistry);
	}

}
