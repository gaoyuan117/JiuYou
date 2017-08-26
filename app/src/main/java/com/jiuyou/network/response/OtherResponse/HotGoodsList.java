package com.jiuyou.network.response.OtherResponse;

import com.jiuyou.network.annotate.ParamName;
import com.jiuyou.network.response.AbstractResponse;

import java.util.List;

/**
 */
public class HotGoodsList extends AbstractResponse {

    @ParamName("modelData")
    private ModelData modelData;

    public ModelData getModelData() {
        return modelData;
    }

    public class ModelData {
        @ParamName("result")
        private List<HotGood> result;

        public List<HotGood> getResult() {
            return result;
        }
    }


    public class HotGood {
        @ParamName("id")
        private String id;
        @ParamName("picUrl") //商品图片url
        private String picUrl;
        @ParamName("price")  //商品价格
        private String price;
        @ParamName("title")  //商品名称
        private String title;
        @ParamName("desc")  //商品描述
        private String desc;
        @ParamName("weight")  //商品weight
        private String weight;

        public String getId() {
            return id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public String getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public String getWeight() {
            return weight;
        }
    }
}
