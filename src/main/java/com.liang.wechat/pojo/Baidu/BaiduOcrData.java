package com.liang.wechat.pojo.Baidu;

import java.util.List;

/**
 * Created by ll.wu on 2018/6/11.
 */
public class BaiduOcrData {
    private String log_id;
    private String words_result_num;
    private List<BaiduWords> words_result;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(String words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<BaiduWords> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<BaiduWords> words_result) {
        this.words_result = words_result;
    }
}
