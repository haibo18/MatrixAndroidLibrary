package com.zhiguwen.matrixlibrary;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a rank 2 tensor;
 */
public class Matrix extends JSONArray {

    private static final long serialVersionUID = -1645831612008191201L;

    /**
     * matrix code [[1,3,3],[2,3,4],[2,3,5],[3,4,6]]
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Matrix(String json) {
        Optional<String> optional = Optional.ofNullable(json);
        if (!optional.isPresent() || json.equals("")) {
            throw new NullPointerException();
        }
        JSONArray array = JSON.parseArray(json);
        JSONArray sub;
        for (Object obj : array) {
            sub = JSON.parseArray(obj.toString());
            this.add(sub);
        }
    }

    /**
     * 使用数据结构的构造方法
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Matrix(List<JSONArray> mat) throws Throwable {
        Optional.ofNullable(mat).orElseThrow(()->new NullPointerException());
        for (JSONArray obj : mat) {
            this.add(obj);
        }
    }

    /**
     * 获取矩阵的列数
     */
    public int getCloumnCount() {
        return isEmpty() ? 0 : getRow(0).size();
    }

    /**
     * 获取矩阵的行数
     */
    public int getRowCount() {
        return isEmpty() ? 0 : size();
    }

    /**
     * 获取矩阵的某一行
     */
    public JSONArray getRow(int index) {
        if (index < 0) {
            throw new ArithmeticException("index < 0");
        }
        return (JSONArray) this.get(index);
    }

    /**
     * 获取矩阵的中某行某列的值
     */
    public double getElement(int rowNum, int cloumnNum) {
        if (rowNum < 0 || cloumnNum < 0) {
            throw new ArithmeticException("row < 0 || cloumn < 0");
        }
        return Double.parseDouble(getRow(rowNum).get(cloumnNum).toString());
    }

    /**
     * 设置矩阵的某一行的值
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setRow(int rowNum, Object element) throws Throwable {
        if (rowNum < 0) {
            throw new ArithmeticException("rowNum < 0");
        }
        Optional.ofNullable(element).orElseThrow(()->new NullPointerException());
        set(rowNum, element);
    }

    /**
     * 设置矩阵某一个值
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setElement(int rowNum, int columnNum, Object element) throws Throwable {
        if (rowNum < 0 || columnNum < 0) {
            throw new ArithmeticException("rowNum < 0 || columnNum < 0");
        }
        Optional.ofNullable(element).orElseThrow(()->new NullPointerException());
        getRow(rowNum).set(columnNum, element);
    }

    /**
     * 获得矩阵的某一列
     */
    public JSONArray getColumn(int columnNum) {
        if (columnNum < 0) {
            throw new ArithmeticException("columnNum < 0");
        }
        JSONArray res = new JSONArray();
        for (Object obj : this) {
            res.add(((JSONArray) obj).get(columnNum));
        }
        return res;
    }

    /**
     * 为矩阵添加一行
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addRow(JSONArray row) throws Throwable {
        Optional.ofNullable(row).orElseThrow(()->new NullPointerException());
        this.add(row);
    }

    /**
     * 为矩阵添加一列
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addColumn(JSONArray jsonArray) throws Throwable {
        Log.i("TAG", "SIZE:" + jsonArray.size());
        Log.i("TAG", "GETROWCOUNT:" + getRowCount());
        Optional.ofNullable(jsonArray).orElseThrow(()->new NullPointerException());
        int i = 0;
        for (Object obj : this) {
            ((JSONArray) obj).add(jsonArray.get(i++));
        }
    }

    /**
     * 设置矩阵的一列
     */
    public void setColumn(int index, JSONArray jsonArray) {
        if (jsonArray.size() != getRowCount()) {
            throw new RuntimeException();
        } else if (index < 0) {
            throw new ArithmeticException("index < 0");
        }
        int i = 0;
        for (Object obj : this) {
            ((JSONArray) obj).set(index, jsonArray.get(i));
            i++;
        }
    }

    /**
     * 求某一列的和
     */
    public double sumByColumn(int columnNum) {
        if (columnNum < 0) {
            throw new ArithmeticException("column < 0");
        }
        double sum = 0;
        for (int i = 0; i < getRowCount(); i++) {
            sum += Double.parseDouble(getRow(i).get(columnNum).toString());
        }
        return sum;
    }

    /**
     * 求某一行的和
     */
    public double sumByRow(int rowNum) {
        if (rowNum < 0) {
            throw new ArithmeticException("rowNum < 0");
        }
        JSONArray array = getRow(rowNum);
        double sum = 0;
        for (Object obj : array) {
            sum += Double.parseDouble(obj.toString());
        }
        return sum;
    }

    /**
     * 获取行向量列表
     */
    public List<JSONArray> getRows() {
        List<JSONArray> arrays = new ArrayList<JSONArray>();
        for (Object objs : this) {
            arrays.add((JSONArray) objs);
        }
        return arrays;
    }

    /**
     * 获取列向量列表
     */
    public List<JSONArray> getColumns() {
        List<JSONArray> arrays = new ArrayList<JSONArray>();
        for (int i = 0; i < getCloumnCount(); i++) {
            JSONArray column = new JSONArray();
            for (int j = 0; j < getRowCount(); j++) {
                column.add(this.getRow(j).get(i));
            }
            arrays.add(column);
        }
        return arrays;
    }

    	@RequiresApi(api = Build.VERSION_CODES.N)
        public static void main(String[] args) throws Throwable {
    		Matrix m = new Matrix("[[1.0,3.1,3.1],[2.3,3.9,4.7],[2.7,3.0,5.5],[3.5,4.5,6.8]]");
    		System.out.println(m.toString());
    		System.out.println(m.getRow(0).toJSONString());
    		System.out.println(m.getElement(0, 2));
    		ArrayList<JSONArray> s = new ArrayList<JSONArray>();
    		JSONArray j = new JSONArray();
    		j.add(1.0);
    		j.add(2.9);
    		j.add(3.8);
    		JSONArray k = new JSONArray();
    		k.add(1.7);
    		k.add(2.6);
    		k.add(3.5);
    		s.add(j);
    		s.add(k);
    		Matrix n = new Matrix(s);
    		System.out.println(n.toString());
    		System.out.println(n.getElement(0, 2));
    		System.out.println(m.sumByRow(1));
    		System.out.println(n.sumByColumn(1));
    		System.out.println(n.getColumn(1));
    		System.out.println(n.getColumn(1));
    		JSONArray x = new JSONArray();
    		x.add(9.9);
    		x.add(9.9);
    		n.addColumn(x);
    		System.out.println(n);
    		System.out.println(n.getRows().toString());
    		System.out.println(n.getColumns().toString());
    	}

}
