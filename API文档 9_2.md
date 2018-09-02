# API文档

```javascript
// ======== user =============== //
/**
 * 获取全部用户信息
 * @url api/user/index, get
 */
const index = function() {}

/**
 * 用户登陆我们的服务器
 * @param {string} code 微信临时凭证
 * @url api/user/login, post
 */
 
const login = function(code) {}
/**
 * 获取某用户信息
 * @param {int} userID 用户ID
 * @url api/user/info, get
 */
 
const infoForID = function(userID) {}

/**
 * 更新用户信息
 * @param {int} userID 用户ID
 * @param {int} gender 性别
 * @param {string} name 姓名
 * @param {string} phoneNum 电话号
 * @url api/user/updateInfo, post
 */
const updateInfo = function(userID,gender,name,phoneNum) {}

/**
 * 用户充值
 * @param {number} amount
 * @param {string} userID 
 * @url api/user/recharge, post
 */
const recharge = function(amount, userID) {}


// =================== course ============ //
/**
 * 获取所有的课程，返回一定数量的的课程
 * @param {number} index 起始编号
 * @param {number} offset 个数
 * @url /api/course, get
 */
const getAllCourses = function(index, offset) {}
/**
 * 获取某个课程
 * @param {number} courseID
 * @url /api/course/findCourse, get
 */
const findCourse = function(courseID) {}

/**
 * 搜索课程
 * @param {string} keyword 
 * @param {number} sortBy 排序方式 0为按时间，1为按分类，2为按热度
 * @param {number} index 
 * @param tag
 * @param {number} offset 
 * @url /api/course/byKeyword, get
 */
const getCoursesByKeyword = function(keyword, sortBy, tag,index, offset) {}

/**添加课程
	*@param name
	* @param location
	* @param teacher
	* @param introduction
	* @param popularity
	* @param tag
	* @param coverPic
	* @param price
	* @param courseNum
	* @url /api/course/addCourse
*/
const addCourse = function (name,location,teacher,introduction,popularity,tag,coverPic,price,courseNum){}

/**修改课程
	*@param ID
	*@param name
	* @param location
	* @param teacher
	* @param introduction
	* @param popularity
	* @param tag
	* @param coverPic
	* @param price
	* @param courseNum
	* @url /api/course/setCourseInfo
*/
const updateCourse = function (ID,name,location,teacher,introduction,popularity,tag,coverPic,price,courseNum){}

/**设置封面
	*@param ID
	* @param coverPic
	* @url /api/course/setCoverPic
	*/
const setCoverPic = function (ID,coverPic){}



// =============== order ================= //
/**
 * 获取某一用户的所有订单
 * @param {*} userID
 * @url order/getUserOrder,get
 */
const getUserOrder = function(userID) {}

/**
 * 获取某一用户的所有收藏
 * @param {*} userID
 * @url order/getUserLike,get
 */
const getUserLike = function(userID) {}

/**
 * 获取某一课程的所有订单
 * @param {int} courseID //课程ID
 * @param {int} term //第几次开课
 * @url order/getAllOrderByCourseID,get
 */
const getAllOrderByCourseID = function(courseID,term) {}

/**
 * 获取报名某一课程的所有用户
 * @param {int} courseID //课程ID
 * @param {int} term //第几次开课
 * @url order/getAllUsersByCourseID,get
 */
const getAllUsersByCourseID = function(courseID,term) {}

/**
 * 添加课程
 * @param {int} courseID //课程ID
 * @param {int} userID //用户ID
 * @param {int} term //第几次开课
 * @url order/addLike,post
 */
const addOrder = function(courseID,userID,term) {}

/**
 * 购买课程
 * @param {int} couponID 使用的代金券ID，没有代金券为-1
 * @param {*} courseID 
 * @param {*} userID 
 * @param {*} term 
 * @url order/buy, post
 */
const buy = function(couponID,courseID, userID) {}

/**
 * 删除订单，移出收藏夹
 * @param {int} orderID //订单ID
 * @url order/deleteOrder,post
 */
const deleteOrder = function(orderID) {}

/**
 * 删除全部订单，移除全部收藏夹
 * @param {int} userID //用户ID
 * @param {boolean} hasPay //true为删除全部订单，false为删除全部收藏夹
 * @url order/deleteAll,post
 */
const deleteAll = function(userID,hasPay) {}


// =============== post ============ //
/**
 * 获取课程下的评论
 * @param {*} courseID 课程ID
 * @param {*} index 起始索引
 * @param {*} offset 长度
 * @url /api/post, get
 */
const index = function(courseID, index, offset) {}
/**
 * 发送评论 
 * @param {number} courseID  
 * @param {string} content 
 * @param {number} userID  
 * @return voID
 * @url /api/post/send, post
 */
const send = function(courseID, content, userID) {} 
/**
 * 发送回复 reply后应该重新调用 getReply刷新页面
 * @param {number} courseID  
 * @param {string} content 
 * @param {number} userID  
 * @param {number} prePostID
 * @return voID
 * @url /api/post/reply, post
 */
const reply = function(courseID, content, userID,prePostID) {} 
/**
 * 获取某帖子下所有回复  
 * @param {number} postID
 * @return List<Post>
 * @url /api/post/getReply, get
 */
const getReply = function(postID) {} 
/**
 * 获取某帖子详情
 * @param {number} postID
 * @return Post
 * @url /api/post/info, get
 */
const indexForID = function(postID) {} 
/**
 * 修改已发布帖子内容 (可调用返回值直接刷新原post)
 * @param {number} postID
 * @param {string} newContent
 * @return Post
 * @url /api/post/edit, post
 */
const modify = function(postID, newContent) {} 
/**
 * 赞帖子
 * @param {number} postID
 * @param {number} userID //点赞用户的ID
 * @return void
 * @url /api/post/like, post
 */
const like = function(postID,userID) {} 
/**
 * 删帖子
 * @param {number} postID
 * @return void
 * @url /api/post/delete, post
 */
const delete = function(postID) {}

// ===========  Coupon  ============= //
/**
 * 获取用户优惠券
 * @param {number} userID
 * @return List<Coupon> 
 * @url /api/coupon/index, get
 */
const index = function(userID) {} 
/**
 * 向用户发优惠券
 * @param {number} userID
 * @param {double} amount
 * @return voID
 * @url /api/coupon/give, post
 */
const give = function(userID,amount) {} 




//没有这个

/**
 * 用户使用优惠券(如果返回失败['使用失败']是已经到期或者已经使用)
 * @param {number} couponID
 * @return voID
 * @url /api/coupon/use, post
 */
const getCoupon = function(couponID) {} 





/**
 * 查某优惠券详情
 * @param {number} couponID
 * @return Coupon
 * @url /api/coupon/info, get
 */
const indexForID = function(couponID) {} 
/**
 * 改变（新）优惠券有效天数（已获得的无法改变）
 * @param {number} newTime  //天数
 * @return Coupon
 * @url /api/coupon/changeLastTime, post
 */
const changeTime = function(newTime) {}
/**
 * 查看优惠券有效天数
 * @return "有效期： " + 天数 + " 天"
 * @url /api/coupon/findLastTime, get
 */
const giveTime = function() {}

// =========== inform ============= //
/**
 * 获取某用户的通知
 * @param {number} userID 
 * @url /api/inform/index get
 */
const index = function(userID) {}
/**
 * 删除通知
 * @param {number} informID 
 * @url /api/inform/delete post
 */
const delete = function(informID) {}
/**
 * 创建新通知
 * @param {number} userID 为-1则通知所有人
 * @param {string} content 
 * @param {string(枚举)} category 通知类别，类型见数据结构文档
 * @param {number} returnID 在获取通知时，返回的有关ID（可以null）
 * @url /api/inform/newInform post
 */
const create = function(userID,content,category,returnID) {}

// =========== file ============= //
/**
 * 更新文件
 * @param {MultipartFile} file
 * @param {String} path
 * @url /api/file/uploadFile post
 */
const uploadFile=function(file,path){}

/**
 * 写入文件
 * @param {byte[]} file
 * @param {String} filePath
 * @param {String} fileName
 * 无映射
 */
const uploadFile=function(file,filePath,fileName){}

// =========== lesson============= //
/**
 * 获取一个课程下的课时
 * @param {*} courseID 
 * @param {*} index
 * @param {*} offset
 * @url /api/course/lesson, get
 */
const getCourseLessons = function(courseID, index ,offset) {}
/**
 * 添加课程
 * @param {*} courseID 
 * @param {*} order
 * @param {*} title
 * @param {*} videoUrl
 * @param {*} description
 * @param {*} coverUrl
 * @url /api/lesson/Addlesson post
 */
const addLesson = function(courseID,order,title,videoUrl,description,coverUrl) {}

/**
 * 设置课程信息
 * @param {*} ID
 * @param {*} title
 * @param {*} videoUrl
 * @param {*} description
 * @param {*} coverUrl
 * @url /api/lesson/setLessonInfo post
 */
const setLesson = function(ID,title,videoUrl,description,coverUrl) {}

/**
 * 设置视频url
 * @param {*} ID
 * @param {*} videoUrl
 * @url /api/lesson/setVideoURL post
 */
const setVIDeoURL = function(ID,videoUrl) {}

/**设置封面url
	*@param ID
	* @param url
	* @url /api/lesson/setCoverPic post
	*/
const setCoverPic = function (ID,url){}

// =========== studyrecord ============= //
==========8.11修改=======================
/**
 * 获取最近的学习记录
 * @param {*} courseID
 * @param {*} userID
 * @url /api/learningRecord, get
 */
const getLatestLearningRecord = function(courseID, userID) {}

/**
 * 更新学习进度，其实就是加一条学习记录
 * @param {*} record
 * @url /api/learningRecord/add, post
 */
const updateLearningProgress = function(record) {}

====8.11添加==========
StudyRecord{
    userID:int,
	courseID:,int
	lessonID:int,
	hour:int,
	minute:int,
	second:int,
}
/**
 * 获取我的课程
 * @param courseID 
 * @param  index
 * @param offset
 * @url /api/course/my get
 */
const getMyCourse = function(userID,index,offset) {}



/**
 * 添加活动
 * @param title
 * @param  url
 * @param isHasCoupon
 * @param coupon
 * @param couponExpiry
 * @param expiry
 * @param requirement
 * @param courseID
 * @param amount
 * @url api/activity/addActivity, post
 */
const addActivity = function(title,url,isHasCoupon,coupon,couponExpiry,expiry,requirement,courseID,amount) {}

/**
 * 更新活动
 * @param ID
 * @param title
 * @param  url
 * @param coupon
 * @param couponExpiry
 * @param expiry
 * @url api/activity/updateActivity, post
 */
const updateActivity = function(ID,title,url,coupon,couponExpiry,expiry) {}

/**
 * 获得所有活动
 * @url api/activity/getAllActivities, get
 */
const getAllActivities = function() {}

/**
 * 获得活动
 * @param ID
 * @url api/activity/getActivity, get
 */
const getActivity = function(ID) {}

/**
 * 删除活动
 * @param ID
 * @url api/activity/deleteActivity, post
 */
const deleteActivity = function(ID) {}
