package com.bin.objectModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

@SerializedName("id")
@Expose
private String id;
@SerializedName("status")
@Expose
private String status;
@SerializedName("task name")
@Expose
private String taskName;
@SerializedName("category")
@Expose
private String category;
@SerializedName("due date")
@Expose
private String dueDate;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getTaskName() {
return taskName;
}

public void setTaskName(String taskName) {
this.taskName = taskName;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public String getDueDate() {
	dueDate= dueDate.replace("\r\n", ""); //assuming the due date values are always appended with "\r\n"
return dueDate;
}

public void setDueDate(String dueDate) {
this.dueDate = dueDate;
}

}