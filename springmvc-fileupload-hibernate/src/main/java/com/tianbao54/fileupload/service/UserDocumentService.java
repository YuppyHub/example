package com.tianbao54.fileupload.service;

import java.util.List;

import com.tianbao54.fileupload.model.UserDocument;

public interface UserDocumentService {

	UserDocument findById(int id);

	List<UserDocument> findAll();
	
	List<UserDocument> findAllByUserId(int id);
	
	void saveDocument(UserDocument document);
	
	void deleteById(int id);
}
