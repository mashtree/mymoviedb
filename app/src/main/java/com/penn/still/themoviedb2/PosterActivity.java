package com.penn.still.themoviedb2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by triyono on 1/2/2018.
 */

public class PosterActivity extends Activity {

    public static final String EXTRA_POSTER = "extra_poster";
    @BindView(R.id.iv_show_poster)
    ImageView ivPoster;
    @BindView(R.id.pb_poster)
    ProgressBar pbPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        ButterKnife.bind(this);
        ivPoster.setVisibility(View.GONE);
        String url = getIntent().getStringExtra(EXTRA_POSTER);

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_filter_b_and_w_black_24dp)
                .into(ivPoster);
        pbPoster.setVisibility(View.GONE);
        ivPoster.setVisibility(View.VISIBLE);

    }
}
