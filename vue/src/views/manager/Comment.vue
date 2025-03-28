<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.filmName" placeholder="请输入电影关键字查询" style="width: 300px"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="primary" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="filmName" label="电影名称" />
        <el-table-column prop="score" label="评分" >
          <template #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half />
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评论内容">
          <template #default="scope">
            <el-button @click="preview(scope.row.comment)">查看内容</el-button> <!-- 修正此处 -->
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户名称" />
        <el-table-column prop="time" label="评论时间" />
        <el-table-column prop="type" label="类型" >
          <template #default="scope">
            <el-tag type="primary" v-if="scope.row.type === '短评'">短评</el-tag>
            <el-tag type="success" v-if="scope.row.type === '长评'">长评</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px">
          <template #default="scope">

            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card">
      <el-pagination background layout="total, prev, pager, next"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum"
                     :total="data.total" @current-change="load" />
    </div>

    <el-dialog v-model="data.formVisibleComment" title="评论内容" width="40%">
      <div v-html="data.commentContent">

      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleComment = false">关闭</el-button> <!-- 修改此处 -->

        </div>
      </template>

    </el-dialog>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  filmName: null,
  fromVisible: false,
  from: {},
  formVisibleComment: false,
  commentContent: null,
  user: JSON.parse(localStorage.getItem('system-user') || '{}')
});

const preview = (comment) => {
  data.commentContent = comment;
  data.formVisibleComment = true;
  // 实现预览逻辑
  console.log(comment);
};

const load = () => {
  const params = {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    userId: data.user.role === 'ADMIN' ? null : data.user.id
  };
  if (data.filmName) {
    params.filmName = data.filmName;
  }
  request.get('/comment/selectPage', {
    params: params
  }).then(res => {
    console.log(res.data);
    data.tableData = res.data.list;
    data.total = res.data.total;
  });
};


load();

const reset = () => {
  data.filmName = null;
  load();
};

const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认吗？', '确认删除', {type: 'warning'}).then(() => {
    request.delete('/comment/delete/' + id).then(res => {
      if (res.code === '200') {
        load();
        ElMessage.success('删除成功');
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {
  });
};
const add = () => {
  request.post('/category/add',data.from).then(res => {
    console.log(res)
    if (res.code === '200') {
      load()
      data.fromVisible = false
      ElMessage.success('添加成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleEdit = (row) => {
  data.from = JSON.parse(JSON.stringify(row))
  data.fromVisible = true
}
const update = () => {
  request.put('/category/update',data.from).then(res => {
    if (res.code === '200') {
      load()
      data.fromVisible = false
      ElMessage.success('更新成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const save = () => {
  data.from.id ? update() : add();
};
</script>
