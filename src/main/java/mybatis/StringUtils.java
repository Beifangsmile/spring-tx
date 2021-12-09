package mybatis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/30 15:40
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 获得用户远程地址
     */
    /**
    public static String getRemoteIp(HttpServletRequest request) {
        String remoteIp = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteIp)) {
            remoteIp = request.getHeader("X-Forwarded-For");
        } else if (isNotBlank(remoteIp)) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        } else if (isNotBlank(remoteIp)) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteIp != null ? remoteIp : request.getRemoteAddr();
    }
    */
    public static String getFormatId(Long id, String prefix) {
        return getFormatId(id, prefix, "000000000000");
    }

    public static String getFormatId(Long id, String prefix, String format) {
        if (id == null) {
            return null;
        } else {
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(format);
            return prefix + decimalFormat.format(id);
        }
    }

    public static String getReplaced(String str, List<String> beforeList, String after) {
        String result = trim(str);
        if (StringUtils.isNotBlank(result)) {
            for (String before : beforeList) {
                result = StringUtils.replace(result, before, after);
            }
        }
        return result;
    }

    public static List<String> getSplitList(String str, String splitter) {
        List<String> list = Lists.newArrayList();
        if (isNotBlank(str)) {
            String[] arr = str.split(splitter);
            for (String item : arr) {
                if (isNotBlank(item) && !list.contains(item.trim())) {
                    list.add(item.trim());
                }
            }
        }
        return list;
    }
/*
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }
*/
    public static String getSheetName(String name) {
        if (isBlank(name)) {
            return null;
        } else {
            return name.trim().replace("/", "").replace("\\", "").replace("?", "").replace("[", "").replace("]", "")
                    .replace("*", "");
        }
    }

    public static String getChineseMoney(BigDecimal money) {
        if (money != null) {
            String s = new DecimalFormat("#.00").format(money.abs());
            s = s.replaceAll("\\.", "");// 将字符串中的"."去掉
            char d[] = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
            String unit = "仟佰拾兆仟佰拾亿仟佰拾万仟佰拾元角分";
            int c = unit.length();
            StringBuffer sb = new StringBuffer(unit);
            for (int i = s.length() - 1; i >= 0; i--) {
                sb.insert(c - s.length() + i, d[s.charAt(i) - 0x30]);
            }
            s = sb.substring(c - s.length(), c + s.length());
            s = s.replaceAll("零[仟佰拾]", "零").replaceAll("零{2,}", "零").replaceAll("零([兆万元Ԫ])", "$1").replaceAll("零[角分]",
                    "");
            if (BigDecimal.ZERO.compareTo(money) == 1) {
                return "负" + s + "整";
            }
            if (BigDecimal.ZERO.compareTo(money) == 0) {
                return "零元整";
            }
            return s + "整";
        }
        return "";
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }
    }

    // 获取列对应的下标字母
    public static String getExcelCellIndex(int index) {
        String rs = "";
        while (index > 0) {
            index--;
            rs = ((char) (index % 26 + 'A')) + rs;
            index = (index - index % 26) / 26;
        }
        return rs;
    }

    public static String toString(Object obj) {
        return Objects.toString(obj, "");
    }

    public static Boolean equal(Object obj1, Object obj2) {
        return toString(obj1).equals(toString(obj2));
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            // 汉字范围 \u4e00-\u9fa5 (中文)
            if (chr1 >= 19968 && chr1 <= 171941) {
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 去除 \u00A0 和 空格
     *
     * @return
     */
    public static String trim(String str) {
        // unicode 化
        if (isEmpty(str)) {
            return null;
        }
        String u00A0 = StringEscapeUtils.unescapeJava("\u00A0");
        str = str.replaceAll(u00A0, "");
        return str.trim();
    }

    public static Boolean isHref(String href) {
        boolean foundMatch = false;
        try {
            foundMatch = href.matches("(?sm)^https?://[-\\w+&@#/%=~_|$?!:,.\\\\*]+$");
        } catch (Exception e) {
            return false;
        }
        return foundMatch;
    }

    public static Boolean isGtZero(String number) {
        boolean foundMatch = false;
        try {
            foundMatch = number
                    .matches("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        } catch (Exception e) {
            return false;
        }
        return foundMatch;
    }

    /**
     * @Title: hideBankNumber
     * @Description: 隐藏银行卡号码 -> 6222****4234
     * @param bankNumber
     *            银行卡号，如：622200000544234
     * @return String 返回类型 隐藏后的卡号，如6222****4234
     */
    public static String hideBankNumber(String bankNumber) {
        if (org.springframework.util.StringUtils.isEmpty(bankNumber)) {
            return "";
        } else if (bankNumber.length() < 8) {
            return bankNumber;
        } else {
            return bankNumber.substring(0, 4) + "****" + bankNumber.substring(bankNumber.length() - 4);
        }
    }

    /**
     * 阿里云附件上传目录默认规则
     *
     * @param timeMillis
     * @param pattern
     * @return
     */
    public static String formatDate(long timeMillis, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.CHINESE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return formatter.format(calendar.getTime());
    }

    /**
     * 将 URL 格式化标准附件方便前端解析
     *
     * @param url
     * @return
     */
//    public static String toAttrJson(String url, String name) {
//        Map<String, Object> map = Maps.newHashMap();
//        // thumbnailUrl
//        // url
//        // size
//        // name
//        map.put("thumbnailUrl", url);
//        map.put("url", url);
//        map.put("size", -1);
//        if (name != null) {
//            map.put("name", name);
//        }
//        return "[" + JsonMapper.getAlwaysMapper().toJson(map) + "]";
//    }

    /**
     * 校验微信公众号的格式
     * 微信号规则：微信账号仅支持6-20个字母、数字、下划线或减号，以字母开头。解释一下，只要是字母开头，可以是纯字母（hjqzHJQZhongjiqiezi），或字母数字混合
     *
     * @param weixinCode
     * @return 返回true表示校验通过
     */
    public static boolean validateWeixin(String weixinCode) {
        if (weixinCode == null) {
            return false;
        }
        // 校验微信号正则
        String judge = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{4,19}+$";
        Pattern pat = Pattern.compile(judge);
        Matcher mat = pat.matcher(weixinCode);
        return mat.matches();
    }

    // 驼峰转下划线
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符集过滤转换
     *
     * @param
     * @return
     */
    /*
    public static List<String> getFilterList(String filterValue) {
        List<String> filterList = Lists.newArrayList();
        if (isNotBlank(filterValue)) {
            filterValue = filterValue.trim().replace(CommonConstant.CHAR_COMMA_FULL, CommonConstant.CHAR_COMMA)
                    .replace(CommonConstant.CHAR_SPACE, CommonConstant.CHAR_COMMA)
                    .replace(CommonConstant.CHAR_SPACE_FULL, CommonConstant.CHAR_COMMA)
                    .replace(CommonConstant.CHAR_ENTER, CommonConstant.CHAR_COMMA);
            String[] filterArr = filterValue.split(CommonConstant.CHAR_COMMA);
            for (String filter : filterArr) {
                if (isNotBlank(filter.trim())) {
                    filterList.add(filter.trim());
                }
            }
        }
        return filterList;
    }

     */

    public static String clearUtf8bm4(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("(?sm)[^\u0000-\uD7FF\uE000-\uFFFF]", "");
    }

    public static void assertString(String object, String objectName) {
        Assert.notNull(object, objectName + " is null");
        Assert.hasLength(object.trim(), objectName + " is empty");
    }

    public static String getStackTraceInfo(Throwable e){
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.getBuffer().toString();
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        String reg = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为数字不判断负数
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if(str == null) {
            return false;
        }
        for(int i=0;i<str.length();i++) {
            if(!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
