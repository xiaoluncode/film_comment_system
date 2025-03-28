<template>
  <div>
    <div style="display: flex; align-items: flex-start; grid-gap: 15px">
        <div  style="flex: 1; width: 0">
          <div class="card" style="padding: 20px;margin-bottom: 10px">
            <div style="display: flex;align-items: center;">
              <div style="font-weight: bold;font-size: 18px;flex: 1">{{data.film.name}}</div>
              <div>
                <el-button type="primary" plain @click="addShortComment">写短评</el-button>
                <el-button type="success" plain @click="addLongComment">写长评</el-button>
              </div>
            </div>
            <div style="margin-bottom: 20px">
              <el-rate v-model="data.film.score" disabled allow-half show-score></el-rate>
            </div>
            <div style="display: flex;grid-gap: 20px;margin-bottom: 20px">
              <img :src="data.film.cover" alt="" style="width: 200px;height: 270px;border-radius: 10px">
              <div style="flex: 1 ;color: #666">
                <div style="margin-bottom: 10px"><strong>导演：</strong>{{data.film.director}}</div>
                <div style="margin-bottom: 10px"><strong>演员：</strong>{{data.film.actors}}</div>
                <div style="margin-bottom: 10px"><strong>类型：</strong>{{data.film.categoryName}}</div>
                <div style="margin-bottom: 10px"><strong>制片国家/地区：</strong>{{data.film.country}}</div>
                <div style="margin-bottom: 10px"><strong>语言：</strong>{{data.film.language}}</div>
                <div style="margin-bottom: 10px"><strong>上映日期：</strong>{{data.film.date}}</div>
                <div style="margin-bottom: 10px"><strong>片长：</strong>{{data.film.duration}}</div>
                <div style="margin-bottom: 10px"><strong>综合评分：</strong>{{data.film.score}}</div>
              </div>
            </div>

            <div style="font-sivsze: 20px;color: rgba(74,168,226,0.78);margin-bottom: 10px">{{data.film.name}}的剧情简介</div>
            <div style="color: #666;text-indent: 2rem; line-height: 24px;text-align: justify">{{data.film.descr}}</div>
          </div>
          <div class="card" style="padding: 20px;margin-bottom: 10px">
            <div style="font-sivsze: 20px;color: rgb(53,189,55);margin-bottom: 10px">{{data.film.name}}的短评 <span style="font-size: 14px;color: #333333">...(全部{{data.totalShort}}条)</span> </div>
            <div v-for="(item, index) in data.commentShortList" :key="index" style="margin-bottom: 15px; padding-bottom: 15px; border-bottom: 1px solid #eee">
              <div style="display: flex; align-items: center; margin-bottom: 5px">
                <span style="font-weight: bold; margin-right: 10px">{{item.userName}}</span>
                <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
                <span style="margin-left: 10px; color: #999; font-size: 14px">{{item.time}}</span>
              </div>
              <div style="color: #666; text-indent: 2em; line-height: 1.5">{{item.comment}}</div>
            </div>
            <el-pagination
              v-if="data.totalShort > 0"
              @current-change="handleCurrentChange"
              :current-page="data.pageNumShort"
              :page-size="data.pageSizeShort"
              layout="prev, pager, next"
              :total="data.totalShort">
            </el-pagination>
          </div>
          <div class="card" style="padding: 20px">
            <div style="font-sivsze: 20px;color: rgb(189,105,53);margin-bottom: 10px">{{data.film.name}}的长评 <span style="font-size: 14px;color: #333333">...(全部{{data.totalLong}}条)</span> </div>
            <div v-for="(item, index) in data.commentLongList" :key="index" style="margin-bottom: 20px; padding-bottom: 20px; border-bottom: 1px solid #eee">
              <div style="display: flex; align-items: center; margin-bottom: 10px">
                <span style="font-weight: bold; margin-right: 10px">{{item.userName}}</span>
                <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
                <span style="margin-left: 10px; color: #999; font-size: 14px">{{item.time}}</span>
              </div>
              <div style="font-size: 18px; font-weight: bold; margin-bottom: 10px">{{item.title}}</div>
              <div style="line-height: 24px; color: #666;" class="line3" v-html="item.comment" ></div>
              <div><span style="color: rgba(74,168,226,0.78); font-size: 14px;cursor: pointer" @click="viewLongComment(item.comment)">查看更多...</span></div>
            </div>
            <el-pagination
              v-if="data.totalLong > 0"
              @current-change="handleLongCurrentChange"
              :current-page="data.pageNumLong"
              :page-size="data.pageSizeLong"
              layout="prev, pager, next"
              :total="data.totalLong">
            </el-pagination>
          </div>
         </div>


        <div style="width: 300px;padding: 20px" class="card">
          <div style="font-size: 20px;margin-bottom: 20px">推荐电影</div>
          <div style="margin-top: 20px;cursor: pointer" v-for="item in data.recommendList" :key="item.id" @click="goDetail(item.id)">
            <img :src="item.cover" alt="" style="width: 100%;border-radius: 10px;margin-bottom: 5px">
            <div style="font-size: 18px">{{item.name}}</div>
            <div >
              <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
            </div>
          </div>
        </div>
    </div>


    <el-dialog v-model="data.formVisibleShort" title="短评" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
        <el-form-item label="打分" >
          <el-rate v-model="data.form.score" allow-half show-score></el-rate>
        </el-form-item>
        <el-form-item label="内容" >
          <el-input :rows="5" type="textarea" v-model="data.form.comment" autocomplete="off" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleShort = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="data.formVisibleLong" title="长评" width="50%">
      <el-form :model="data.formLong" label-width="80px" style="padding-right: 20px">
        <el-form-item label="标题" >
          <el-input v-model="data.formLong.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="打分" >
          <el-rate v-model="data.formLong.score" allow-half show-score></el-rate>
        </el-form-item>
        <el-form-item label="内容" >
          <div style="border: 1px solid #ccc">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editorRef"
              :defaultConfig="toolbarConfig"
              mode="default"
            />
            <Editor
              style="height: 500px; overflow-y: hidden;"
              v-model="valueHtml"
              :defaultConfig="editorConfig"
              mode="default"
              @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleLong = false">取消</el-button>
          <el-button type="primary" @click="saveLong">发布长评</el-button>
        </div>
      </template>
    </el-dialog>


    <el-dialog v-model="data.formVisibleView" title="查看长评" width="50%">
      <div style="line-height: 24px" class="longComment">
        <div v-html="data.comment"></div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleView = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, onMounted, onBeforeUnmount, ref, shallowRef } from "vue";
import router from "@/router";
import request from "@/utils/request";
import { ElMessage } from 'element-plus';
import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

// 内容 HTML
const valueHtml = ref('<p>hello</p>');

// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p></p>';
  }, 1500);
});

const toolbarConfig = {};
const editorConfig = { placeholder: '请输入内容...' };

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

const handleCreated = (editor) => {
  editorRef.value = editor; // 记录 editor 实例，重要！
};


const data = reactive({
  id: null,
  film: {},
  recommendList: [],
  formVisibleShort: false,
  form: {},
  pageNumShort: 1,
  pageSizeShort: 3,
  totalShort: 0,
  commentShortList: [],
  formVisibleLong: false,
  formLong: {},
  pageNumLong: 1,
  pageSizeLong: 3,
  totalLong: 0,
  commentLongList: [],
  user:JSON.parse(localStorage.getItem('system-user') || '{}'),
  formVisibleView: false,
  comment: null
})

const viewLongComment = (comment) => {
  data.comment = comment
  data.formVisibleView = true;
}

const addShortComment = () => {
  data.form={ type:'短评'}
  data.formVisibleShort = true;
}

const handleCurrentChange = (pageNum) => {
  data.pageNumShort = pageNum;
  loadShortComment();
}

const save = () => {
  data.form.filmId = data.id
  data.form.userId=data.user.id
  request.post('/comment/add', data.form).then(res => {
    if (res.code === '200') {
      data.formVisibleShort = false;
      ElMessage.success('添加成功')
      loadShortComment()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const loadShortComment = () => {
  request.get('/comment/selectPage',{
    params: {
      pageNum: data.pageNumShort,
      pageSize: data.pageSizeShort,
      filmId: data.id,
      type:'短评',
    }
  }).then(res => {
    data.commentShortList = res.data.list
    data.totalShort = res.data.total
  })
}

const addLongComment = () => {
  data.formLong = { type: '长评' }
  data.formVisibleLong = true
}

const handleLongCurrentChange = (pageNum) => {
  data.pageNumLong = pageNum;
  loadLongComment();
}

const saveLong = () => {
  if (!data.formLong.title?.trim()) {
    ElMessage.warning('请输入标题')
    return
  }
  if (!valueHtml.value?.trim()) {
    ElMessage.warning('请输入影评内容')
    return
  }

  data.formLong.comment = valueHtml.value; // 将编辑器内容赋值给formLong.comment
  data.formLong.filmId = data.id
  data.formLong.userId = data.user.id
  request.post('/comment/add', data.formLong).then(res => {
    if (res.code === '200') {
      data.formVisibleLong = false;
      ElMessage.success('发布成功')
      loadLongComment()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const loadLongComment = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNumLong,
      pageSize: data.pageSizeLong,
      filmId: data.id,
      type: '长评',
    }
  }).then(res => {
    data.commentLongList = res.data.list
    data.totalLong = res.data.total
  })
}

onMounted(() => {
  const id = parseInt(router.currentRoute.value.query.id, 10);
  if (id) {
    data.id = id;
    request.get('/film/selectById/' + data.id).then(res => {
      data.film = res.data;
    }).catch(err => {
      console.error('获取电影详情失败:', err);
    });

    request.get('/film/selectRecommend/' + data.id).then(res => {
      data.recommendList = res.data;
    }).catch(err => {
      console.error('获取推荐电影失败:', err);
    });

    loadShortComment();
    loadLongComment();
  } else {
    console.error('电影ID未找到');
  }
});

const goDetail = (id) => {
  location.href = '/filmDetail?id=' + id;
}
</script>

<style>
.longComment img {
  width: 100%;
}
</style>