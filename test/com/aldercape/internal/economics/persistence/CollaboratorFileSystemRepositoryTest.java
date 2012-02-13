package com.aldercape.internal.economics.persistence;

import static com.aldercape.internal.economics.model.CustomModelAsserts.*;

import java.io.File;

import org.junit.Test;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Collaborator;
import com.aldercape.internal.economics.model.NoMatchingRecordException;
import com.aldercape.internal.economics.ui.__TestObjectMother;

public class CollaboratorFileSystemRepositoryTest extends BaseFileSystemRepositoryTest<Collaborator> {

	private CollaboratorFileSystemRepository repository;

	@Test(expected = NoMatchingRecordException.class)
	public void shouldThrowExceptionIfCollaboratorIsNotPresentOnGet() {
		repository.findByEmail("No such collaborator");
	}

	@Test
	public void testFindByEmail() {
		__TestObjectMother objectMother = new __TestObjectMother();
		getRepository().add(objectMother.me());
		getRepository().add(objectMother.other());
		assertCollaboratorEquals(objectMother.me(), repository.findByEmail(objectMother.me().email()));
		assertCollaboratorEquals(objectMother.other(), repository.findByEmail(objectMother.other().email()));
	}

	@Override
	protected Collaborator getFirstEntry() {
		return new __TestObjectMother().me();
	}

	@Override
	protected Collaborator getSecondEntry() {
		return new __TestObjectMother().other();
	}

	@Override
	protected void createNewRepository(File newFile) {
		this.repository = new CollaboratorFileSystemRepository(newFile);
	}

	@Override
	protected String getFirstEntryJson() {
		return "{\"firstname\":\"Me\",\"lastname\":\"Surname\",\"email\":\"me@mycompany.com\"}";
	}

	@Override
	protected String getSecondEntryJson() {
		return "{\"firstname\":\"Other\",\"lastname\":\"Something\",\"email\":\"other@othercompany.com\"}";
	}

	@Override
	protected BaseRepository<Collaborator> getRepository() {
		return repository;
	}

	@Override
	protected void assertEntryEquals(Collaborator expected, Collaborator actual) {
		assertCollaboratorEquals(expected, actual);
	}

}
