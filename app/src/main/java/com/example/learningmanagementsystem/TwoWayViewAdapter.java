package com.example.learningmanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
① 创建一个继承RecyclerView.Adapter<VH>的Adapter类
② 创建一个继承RecyclerView.ViewHolder的静态内部类
③ 在Adapter中实现3个方法：
   onCreateViewHolder()
   onBindViewHolder()
   getItemCount()
*/
public class TwoWayViewAdapter extends TwoWayView.Adapter<TwoWayView.ViewHolder>{
    private Context context;

    private List<String> mlist;
    private List<String> mFilterList = new ArrayList<>();

    private View inflater;

    public enum Item_Type {
        RECYCLEVIEW_ITEM_TYPE_1,
        RECYCLEVIEW_ITEM_TYPE_2,
        RECYCLEVIEW_ITEM_TYPE_3,
        RECYCLEVIEW_ITEM_TYPE_4,
        RECYCLEVIEW_ITEM_TYPE_5
    }

    private MyOnItemClickListener itemClickListener;
    //构造方法，传入数据
    public TwoWayViewAdapter(Context context, List<String> list){
        this.context = context;
        this.mFilterList = list;
        this.mlist = list;

    }


    /**
     * 列表点击事件
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType :不同ItemView的类型
     * @return
     */
    @Override
    public TwoWayView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal()) {

            inflater = LayoutInflater.from(context).inflate(R.layout.item_text1,parent,false);
            TwoWayViewAdapter.ViewHolderA viewHolder = new TwoWayViewAdapter.ViewHolderA(inflater);
            return viewHolder;

        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal()) {

            inflater = LayoutInflater.from(context).inflate(R.layout.item_text2,parent,false);
            TwoWayViewAdapter.ViewHolderB viewHolder = new TwoWayViewAdapter.ViewHolderB(inflater);
            return viewHolder;
        } else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal()) {
            inflater = LayoutInflater.from(context).inflate(R.layout.item_pic,parent,false);
            TwoWayViewAdapter.ViewHolderC viewHolder = new TwoWayViewAdapter.ViewHolderC(inflater);
            return viewHolder;
        }else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_4.ordinal()) {
            inflater = LayoutInflater.from(context).inflate(R.layout.item_text3,parent,false);
            TwoWayViewAdapter.ViewHolderD viewHolder = new TwoWayViewAdapter.ViewHolderD(inflater);
            return viewHolder;
        }else if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_5.ordinal()) {
            inflater = LayoutInflater.from(context).inflate(R.layout.item_pic,parent,false);
            TwoWayViewAdapter.ViewHolderE viewHolder = new TwoWayViewAdapter.ViewHolderE(inflater);
            return viewHolder;
        }

        return null;
    }
    /**
     * 绑定数据：可以直接拿到已经绑定控件的Viewholder对象
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(final TwoWayView.ViewHolder holder, int position) {
        //将数据和控件绑定
        if (holder instanceof ViewHolderA) {
            ((ViewHolderA) holder).text1.setText(mFilterList.get(position));
        } else if (holder instanceof ViewHolderB) {
            ((ViewHolderB) holder).text2.setText("Lecturer:"+mFilterList.get(position));
        } else if (holder instanceof ViewHolderC) {
           // ((ViewHolderC) holder).image.setImageResource(R.drawable.pic1);
            Glide.with(context)
                    .load("http://10.0.2.2:8080/elearn/materials/"+mFilterList.get(position)+"/file")
                    .placeholder(R.drawable.loading)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            ((ViewHolderC) holder).image.setImageDrawable(resource);
                        }
                    });

        } else if (holder instanceof ViewHolderD) {
            ((ViewHolderD) holder).text3.setText(mFilterList.get(position));
        } else if (holder instanceof ViewHolderE) {
            ((ViewHolderE) holder).image1.setImageResource(R.drawable.play);


        }
        SpannableGridLayoutManager.LayoutParams lp =
                (SpannableGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if(position%5==0) {
            lp.rowSpan = 1;
            lp.colSpan = 3;

        }
        else if(position%5==1) {
            lp.rowSpan = 1;
            lp.colSpan = 2;

        }
        else if(position%5==2) {
            lp.rowSpan = 1;
            lp.colSpan = 1;

        }
        else if(position%5==3) {
            lp.rowSpan = 1;
            lp.colSpan = 2;

        }
        else if(position%5==4) {
            lp.rowSpan = 1;
            lp.colSpan = 1;

        }
        holder.itemView.setLayoutParams(lp);
        /*自定义item的点击事件不为null，设置监听事件*/
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

    }
    //返回值赋值给onCreateViewHolder的参数 viewType
    @Override
    public int getItemViewType(int position) {

        if (position%5 == 0) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal();
        } else if (position%5 == 1) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_2.ordinal();
        } else if (position%5 == 2) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_3.ordinal();
        } else if (position%5 == 3) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_4.ordinal();
        } else if (position%5 == 4) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_5.ordinal();
        }
        return -1;
    }
    @Override
    public int getItemCount() {
        //返回Item总条数
        return mFilterList.size();
    }

    class ViewHolderA extends TwoWayView.ViewHolder {
        public TextView text1;

        public ViewHolderA(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.item_text1);
        }
    }


    class ViewHolderB extends TwoWayView.ViewHolder {

        public TextView text2;

        public ViewHolderB(View itemView) {
            super(itemView);
            text2 = (TextView) itemView.findViewById(R.id.item_text2);
        }
    }

    class ViewHolderC extends TwoWayView.ViewHolder {

        public ImageView image;

        public ViewHolderC(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_pic);
        }
    }
    class ViewHolderD extends TwoWayView.ViewHolder {

        public TextView text3;

        public ViewHolderD(View itemView) {
            super(itemView);
            text3 = (TextView) itemView.findViewById(R.id.item_text3);
        }
    }
    class ViewHolderE extends TwoWayView.ViewHolder {

        public ImageView image1;

        public ViewHolderE(View itemView) {
            super(itemView);
            image1 = (ImageView) itemView.findViewById(R.id.item_pic);
        }
    }

















}
