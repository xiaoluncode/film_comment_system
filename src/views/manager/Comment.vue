const load = () => {
  const params = {
    pageNum: data.pageNum,
    pageSize: data.pageSize
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