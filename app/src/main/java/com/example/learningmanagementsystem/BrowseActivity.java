package com.example.learningmanagementsystem;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import java.util.ArrayList;
import java.util.List;
public class BrowseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;//声明RecyclerView
    private RecycleAdapterDome adapterDome;//声明适配器
    private Context context;


    private String[] str = new String[]{
            "address", "apple juice", "apple pie", "abalone", "bread", "brandy", "Blueberry", "Banana", "chocolate", "cake", "chicken", "cheese", "Durian",
            "Dim Sam", "Dumpling", "duck", "egg", "English muffin", "eggplant", "French toast", "fish", "fig", "fruit"
    };

    private List<String> list;
    private void initList() {
        list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            list.add(str[i]);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_browse);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        initList();
        adapterDome = new RecycleAdapterDome(context,list);
        /*
        与ListView效果对应的可以通过LiManager来设置
        与GridView效果对应的可以通过GridLayoutManager来设nearLayout置
        与瀑布流对应的可以通过StaggeredGridLayoutManager来设置
        */
        //LinearLayoutManager man  ager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        adapterDome.appendList(list);
        recyclerView.setAdapter(adapterDome);

        adapterDome.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Intent it = new Intent(BrowseActivity.this,ClassMessageActivity.class);
                String name = list.get(position);
                String text = name;
                for(int i = 0;i<50;i++){
                    text = text + name;
                }
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("text", text);
                //Intent携带数据包
                it.putExtras(bundle);
                startActivity(it);

            }
        });
        RadioButton search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        EditText et = findViewById(R.id.editText2);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                adapterDome.getFilter().filter(sequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
