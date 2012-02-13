package com.aldercape.internal.economics.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aldercape.internal.economics.model.BaseRepository;
import com.aldercape.internal.economics.model.Project;
import com.aldercape.internal.economics.persistence.JsonStorage.ElementStorage;
import com.google.gson.reflect.TypeToken;

public class ProjectFileSystemRepository implements BaseRepository<Project>, ElementStorage<Project> {

	private JsonStorage<Project> jsonStorage;
	private List<Project> projects = new ArrayList<>();

	public ProjectFileSystemRepository(File file, RepositoryRegistry repositoryRegistry) {
		jsonStorage = new JsonStorage<>(file, false, this, repositoryRegistry, new TypeToken<Map<Long, Project>>() {
		});
		jsonStorage.populateCache();
	}

	@Override
	public List<Project> getAll() {
		return projects;
	}

	@Override
	public void addListener(com.aldercape.internal.economics.model.BaseRepository.Listener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(Project t) {
		jsonStorage.addToStorage(t);
		jsonStorage.writeAllToFile();
	}

	@Override
	public void addWithoutCache(Project t) {
		projects.add(t);
	}

	@Override
	public boolean isSame(Project value, Project ref) {
		// TODO Auto-generated method stub
		return false;
	}

}
