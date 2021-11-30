package com.example.demo.service.filemangement;

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
import com.example.demo.model.filemanagement.FileModel;
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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

	 */
	public List<FileModel> getAllFiles() {

		return mongoTemplate.findAll(FileModel.class);
	}

	/**
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
	 * Method to Route the process to corresponding handler to get the complete
	 * response
	 *
	 * @param context APIContext with the request details.
	 * @param view    contains the request body.
	 * @return ResponseEntity with respective status and information.
	 * @throws APIException handles Exception from Handler class.

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
