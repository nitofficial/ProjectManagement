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

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.FileModel;
import com.mongodb.BasicDBObject;

@Service
public class FileService {
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	public FileService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;

	}

	public FileModel addFile(FileModel filemodel) {

		if (filemodel.getFilesubdocument().size() == 0) {
			throw new BadRequestException("Could not read any file");
		} else {

			Update update = new Update().addToSet("filesubdocument", filemodel.getFilesubdocument().get(0));

			Query q = new Query();
			q.addCriteria(Criteria.where("defect_id").is(filemodel.getDefect_id()));

			FileModel addedEntity = mongoOperations.findAndModify(q, update, options().returnNew(true).upsert(true),
					FileModel.class);

			return addedEntity;
		}
	}

	public FileModel updateFileByIdAndAssetId(FileModel filemodel, String defect_id, String asset_id) {

		if (filemodel.getFilesubdocument().size() == 0) {
			throw new BadRequestException("Could not read any file");
		} else {

			Query query = new Query();
			query.addCriteria(Criteria.where("defect_id").is(defect_id).and("filesubdocument")
					.elemMatch(Criteria.where("asset_id").is(asset_id)));

			Update update = new Update().set("filesubdocument.$", filemodel.getFilesubdocument().get(0));

			FileModel addedEntity = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true),
					FileModel.class);
			return addedEntity;
		}

	}

	public List<FileModel> getAllFiles() {

		return mongoTemplate.findAll(FileModel.class);
	}

	public FileModel getFileById(String defect_id) {

		Query q = new Query();
		q.addCriteria(Criteria.where("defect_id").is(defect_id));

		return mongoTemplate.findOne(q, FileModel.class);
	}

	public FileModel getFileByAssetId(String defect_id, String asset_id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("defect_id").is(defect_id));
		query.addCriteria(Criteria.where("filesubdocument").elemMatch(Criteria.where("asset_id").is(asset_id)));

		FileModel reqEntity = mongoTemplate.findOne(query, FileModel.class);
		return reqEntity;
	}

	public String deleteAllFiles(String defect_id) {

		Query q = new Query();
		q.addCriteria(Criteria.where("defect_id").is(defect_id));
		FileModel deletedEntity = mongoTemplate.findAndRemove(q, FileModel.class);

		return (deletedEntity != null) ? "FILE WITH" + deletedEntity.getDefect_id() + " deleted from the database"
				: "File not found";

	}

	public String deleteFileByAssetId(String defect_id, String asset_id) {

		Query query = new Query();
		query.addCriteria(Criteria.where("defect_id").is(defect_id));

		Update update = new Update().pull("filesubdocument", new BasicDBObject("asset_id", asset_id));

		mongoOperations.upsert(query, update, FileModel.class);
		return "FILE WITH defect_id" + defect_id + " is Removed";
	}

}
