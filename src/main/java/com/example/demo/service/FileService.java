/**
	 * @author Nithish	
*/
package com.example.demo.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.constants.Constants;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.FileModel;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.mongodb.MongoQueryException;
import com.mongodb.client.result.UpdateResult;

@Service
public class FileService {
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	public FileService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;

	}
	
	/**
	 * Method to add File into db
	 * @param FileModel which contains the file details.
	 * @return FileModel with respective status and information.
	 * @throws BadRequestException handles Exception.

	 */

	public FileModel addFile(FileModel filemodel) {

		if (filemodel.getFilesubdocument().isEmpty()) {
			throw new BadRequestException("Could not read any file");
		} else {

			Update update = new Update().addToSet(Constants.FILE_SUB_DOCUMENT, filemodel.getFilesubdocument().get(0));

			Query q = new Query();
			q.addCriteria(Criteria.where(Constants.DEFECT_ID).is(filemodel.getDefect_id()));

			return mongoOperations.findAndModify(q, update, options().returnNew(true).upsert(true), FileModel.class);

		}
	}

	/**
	 * Method to update a existing File in db
	 * @param FileModel which contains the new file details.
	 * @param String defect_id, String asset_id of file to be replaced.
	 * @return FileModel with respective status and information.
	 * @throws BadRequestException handles Exception.

	 */
	public FileModel updateFileByIdAndAssetId(FileModel filemodel, String defect_id, String asset_id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(Constants.DEFECT_ID).is(defect_id).and("filesubdocument")
					.elemMatch(Criteria.where(Constants.ASSET_ID).is(asset_id)));

			Update update = new Update().set("filesubdocument.$", filemodel.getFilesubdocument().get(0));

			return mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true),
					FileModel.class);
		} catch (Exception e) {
			throw new BadRequestException("File not found");

		}
	}
	
	/**
	 * Method to get all Files from db
	 * @return List<FileModel> with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	public List<FileModel> getAllFiles() {

		return mongoTemplate.findAll(FileModel.class);
	}

	/**
	 * Method to get all Files for a defect_id from db
	 * @return FileModel with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	public FileModel getFileById(String defect_id) {

		Query q = new Query();
		q.addCriteria(Criteria.where(Constants.DEFECT_ID).is(defect_id));

		FileModel reqEntity = mongoTemplate.findOne(q, FileModel.class);
		if (reqEntity != null) {
			return reqEntity;
		} else {
			throw new BadRequestException("File not found");
		}

	}
	
	/**
	 * Method to get a file with defect_id and unique asset_id.
	 * @return FileModel with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	public FileModel getFileByAssetId(String defect_id, String asset_id) {
		Query query = new Query();
		query.addCriteria(Criteria.where(Constants.DEFECT_ID).is(defect_id));
		query.addCriteria(
				Criteria.where(Constants.FILE_SUB_DOCUMENT).elemMatch(Criteria.where(Constants.ASSET_ID).is(asset_id)));

		FileModel reqEntity = mongoTemplate.findOne(query, FileModel.class);
		if (reqEntity != null) {
			return reqEntity;
		} else {
			throw new BadRequestException("File not found");
		}
	}
	
	/**
	 * Method to delete all files with defect_id.
	 * @return String with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	
	public String deleteAllFiles(String defect_id) {

		Query q = new Query();
		q.addCriteria(Criteria.where(Constants.DEFECT_ID).is(defect_id));
		FileModel deletedEntity = mongoTemplate.findAndRemove(q, FileModel.class);

		if (deletedEntity != null) {
			return "File is deleted";
		} else {
			throw new BadRequestException("File not found");
		}

	}
	
	/**
	 * Method to delete a files with defect_id and unique asser_id.
	 * @return String with respective status and information.
	 * @throws BadRequestException handles Exception.
	 */
	

	public String deleteFileByAssetId(String defect_id, String asset_id) {

		Query query = new Query();
		query.addCriteria(Criteria.where(Constants.DEFECT_ID).is(defect_id));

		Update update = new Update().pull(Constants.FILE_SUB_DOCUMENT, new BasicDBObject(Constants.ASSET_ID, asset_id));

		UpdateResult deletedEntity = mongoOperations.updateFirst(query, update, FileModel.class);

		if (deletedEntity.getMatchedCount() == 0 || deletedEntity.getModifiedCount() == 0) {
			throw new BadRequestException("File not found");
		} else {
			return "FILE WITH defect_id " + defect_id + " is Removed";
		}

	}

}
