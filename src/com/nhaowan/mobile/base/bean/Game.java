package com.nhaowan.mobile.base.bean;

import java.io.Serializable;
import java.util.List;

import com.haha.frame.utils.Element;
import com.haha.frame.utils.ElementList;

public class Game implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Element("GAME_ID")
	private String id;
	@Element("GAME_NAME")
	private String name;
	@Element("GAME_LOGO")
	private String logo;
	@Element("GAME_DOWNLOAD")
	private String download;
	@Element("GAME_DESC")
	private String desc;
	@Element("GAME_CLASS")
	private String clazz;
	@Element("GAME_FREE_TYPE")
	private String freeType;
	@Element("GAME_NAME_TAG")
	private String nameTag;
	@Element("GAME_PACKAGE_SIZE")
	private String packageSize;
	@Element("GAME_VERSION")
	private String version;
	@Element("GAME_DEVELOPERS")
	private String developers;
	@Element("GAME_LANGUAGE")
	private String language;
	@Element("APP_VERSION")
	private String appVersion;
	@ElementList("GAME_IMG")
	private List<String> img;
	@Element("GAME_PINGFEN")
	private String pingfen;
	@Element("GAME_ADD_DATE")
	private String addDate;
	@Element("GAME_DESC_SHORT")
	private String descShort;
	@Element("GAME_FLAG")
	private String flag;
	@Element("GAME_INSTALL")
	private String install;
	@Element("NEW")
	private String isNew;
	@Element("COMMENT_NUM")
	private String commentNum;
	@Element("DOWNLOADS_NUM")
	private String downloadsNum;
	@Element("IS_RECOMMENT")
	private String isReComment;
	@ElementList("GAME_MORE")
//	private List<GameADImage> gameMore;
	
	private String gameAdId;
	
	public String getGameAdId() {
		return gameAdId;
	}
	public void setGameAdId(String gameAdId) {
		this.gameAdId = gameAdId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getFreeType() {
		return freeType;
	}
	public void setFreeType(String freeType) {
		this.freeType = freeType;
	}
	public String getNameTag() {
		return nameTag;
	}
	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDevelopers() {
		return developers;
	}
	public void setDevelopers(String developers) {
		this.developers = developers;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public List<String> getImg() {
		return img;
	}
	public void setImg(List<String> img) {
		this.img = img;
	}
	public String getPingfen() {
		return pingfen;
	}
	public void setPingfen(String pingfen) {
		this.pingfen = pingfen;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getDescShort() {
		return descShort;
	}
	public void setDescShort(String descShort) {
		this.descShort = descShort;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getInstall() {
		return install;
	}
	public void setInstall(String install) {
		this.install = install;
	}
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}
	public String getDownloadsNum() {
		return downloadsNum;
	}
	public void setDownloadsNum(String downloadsNum) {
		this.downloadsNum = downloadsNum;
	}
	public String getIsReComment() {
		return isReComment;
	}
	public void setIsReComment(String isReComment) {
		this.isReComment = isReComment;
	}
//	public List<GameADImage> getGameMore() {
//		return gameMore;
//	}
//	public void setGameMore(List<GameADImage> gameMore) {
//		this.gameMore = gameMore;
//	}
	
	
	
}
