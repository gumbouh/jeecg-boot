package org.jeecg.modules.demo.gumbo.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: picture
 * @Author: jeecg-boot
 * @Date:   2021-07-09
 * @Version: V1.0
 */
@Data
@TableName("picture")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="picture对象", description="picture")
public class Picture implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**本地文件路径*/
	@Excel(name = "本地文件路径", width = 15)
    @ApiModelProperty(value = "本地文件路径")
    private java.lang.String localPath;
	/**图片类型，0背景1头像*/
	@Excel(name = "图片类型，0背景1头像", width = 15)
    @ApiModelProperty(value = "图片类型，0背景1头像")
    private java.lang.Integer type;
	/**tag*/
	@Excel(name = "tag", width = 15)
    @ApiModelProperty(value = "tag")
    private java.lang.String tag;
	/**可用性，0不可用，1可用*/
	@Excel(name = "可用性，0不可用，1可用", width = 15)
    @ApiModelProperty(value = "可用性，0不可用，1可用")
    private java.lang.Integer usability;
	/**绑定账号*/
	@Excel(name = "绑定账号", width = 15)
    @ApiModelProperty(value = "绑定账号")
    private java.lang.String accountId;
}
