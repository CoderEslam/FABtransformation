package com.doubleclick.fabtransformation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.doubleclick.fab_transformation.FabTransformation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.InjectView;
import butterknife.OnClick;

public class TransformToToolbarActivity extends BaseActivity {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.toolbar_footer)
    View toolbarFooter;
    @InjectView(R.id.scroll_view)
    ScrollView scrollView;

    private boolean isTransforming;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, TransformToToolbarActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    int getLayoutResId() {
        return R.layout.activity_transform_to_toolbar;
    }

    @Override
    protected void initView() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (fab.getVisibility() != View.VISIBLE && !isTransforming) {
                            FabTransformation.with(fab)
                                    .setListener(new FabTransformation.OnTransformListener() {
                                        @Override
                                        public void onStartTransform() {
                                            isTransforming = true;
                                        }

                                        @Override
                                        public void onEndTransform() {
                                            isTransforming = false;
                                        }
                                    })
                                    .transformFrom(toolbarFooter);
                        }
                    }
                });
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            FabTransformation.with(fab).transformTo(toolbarFooter);
        }
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).transformFrom(toolbarFooter);
            return;
        }
        super.onBackPressed();
    }
}
