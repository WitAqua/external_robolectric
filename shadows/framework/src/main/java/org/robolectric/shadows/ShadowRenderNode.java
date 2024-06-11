package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.P;
import static android.os.Build.VERSION_CODES.Q;

import android.graphics.Camera;
import android.graphics.Matrix;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(
    className = "android.view.RenderNode",
    isInAndroidSdk = false,
    minSdk = LOLLIPOP,
    maxSdk = P)
public class ShadowRenderNode {
  private static final float NON_ZERO_EPSILON = 0.001f;

  private float alpha = 1f;
  private float cameraDistance;
  private boolean clipToOutline;
  private float elevation;
  private boolean overlappingRendering;
  private boolean pivotExplicitlySet;
  private float pivotX;
  private float pivotY;
  private float rotation;
  private float rotationX;
  private float rotationY;
  private float scaleX = 1f;
  private float scaleY = 1f;
  private float translationX;
  private float translationY;
  private float translationZ;
  private int left;
  private int top;
  private int right;
  private int bottom;

  @Implementation
  protected boolean setAlpha(float alpha) {
    this.alpha = alpha;
    return true;
  }

  @Implementation
  protected float getAlpha() {
    return alpha;
  }

  @Implementation
  protected boolean setCameraDistance(float cameraDistance) {
    this.cameraDistance = cameraDistance;
    return true;
  }

  @Implementation
  protected float getCameraDistance() {
    return cameraDistance;
  }

  @Implementation
  protected boolean setClipToOutline(boolean clipToOutline) {
    this.clipToOutline = clipToOutline;
    return true;
  }

  @Implementation
  protected boolean getClipToOutline() {
    return clipToOutline;
  }

  @Implementation
  protected boolean setElevation(float lift) {
    elevation = lift;
    return true;
  }

  @Implementation
  protected float getElevation() {
    return elevation;
  }

  @Implementation
  protected boolean setHasOverlappingRendering(boolean overlappingRendering) {
    this.overlappingRendering = overlappingRendering;
    return true;
  }

  @Implementation
  protected boolean hasOverlappingRendering() {
    return overlappingRendering;
  }

  @Implementation
  protected boolean setRotation(float rotation) {
    this.rotation = rotation;
    return true;
  }

  @Implementation
  protected float getRotation() {
    return rotation;
  }

  @Implementation
  protected boolean setRotationX(float rotationX) {
    this.rotationX = rotationX;
    return true;
  }

  @Implementation
  protected float getRotationX() {
    return rotationX;
  }

  @Implementation
  protected boolean setRotationY(float rotationY) {
    this.rotationY = rotationY;
    return true;
  }

  @Implementation
  protected float getRotationY() {
    return rotationY;
  }

  @Implementation
  protected boolean setScaleX(float scaleX) {
    this.scaleX = scaleX;
    return true;
  }

  @Implementation
  protected float getScaleX() {
    return scaleX;
  }

  @Implementation
  protected boolean setScaleY(float scaleY) {
    this.scaleY = scaleY;
    return true;
  }

  @Implementation
  protected float getScaleY() {
    return scaleY;
  }

  @Implementation
  protected boolean setTranslationX(float translationX) {
    this.translationX = translationX;
    return true;
  }

  @Implementation
  protected boolean setTranslationY(float translationY) {
    this.translationY = translationY;
    return true;
  }

  @Implementation
  protected boolean setTranslationZ(float translationZ) {
    this.translationZ = translationZ;
    return true;
  }

  @Implementation
  protected float getTranslationX() {
    return translationX;
  }

  @Implementation
  protected float getTranslationY() {
    return translationY;
  }

  @Implementation
  protected float getTranslationZ() {
    return translationZ;
  }

  @Implementation
  protected boolean isPivotExplicitlySet() {
    return pivotExplicitlySet;
  }

  @Implementation(minSdk = P)
  protected boolean resetPivot() {
    this.pivotExplicitlySet = false;
    this.pivotX = 0;
    this.pivotY = 0;
    return true;
  }

  @Implementation
  protected boolean setPivotX(float pivotX) {
    this.pivotX = pivotX;
    this.pivotExplicitlySet = true;
    return true;
  }

  @Implementation
  protected float getPivotX() {
    return pivotX;
  }

  @Implementation
  protected boolean setPivotY(float pivotY) {
    this.pivotY = pivotY;
    this.pivotExplicitlySet = true;
    return true;
  }

  @Implementation
  protected float getPivotY() {
    return pivotY;
  }

  @Implementation
  protected boolean setLeft(int left) {
    this.left = left;
    return true;
  }

  @Implementation(minSdk = Q)
  protected int getLeft() {
    return left;
  }

  @Implementation
  protected boolean setTop(int top) {
    this.top = top;
    return true;
  }

  @Implementation(minSdk = Q)
  protected int getTop() {
    return top;
  }

  @Implementation
  protected boolean setRight(int right) {
    this.right = right;
    return true;
  }

  @Implementation
  protected boolean setBottom(int bottom) {
    this.bottom = bottom;
    return true;
  }

  public int getWidth() {
    return right - left;
  }

  public int getHeight() {
    return bottom - top;
  }

  @Implementation
  protected boolean setLeftTopRightBottom(int left, int top, int right, int bottom) {
    return setPosition(left, top, right, bottom);
  }

  public boolean setPosition(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    return true;
  }

  @Implementation
  protected boolean offsetLeftAndRight(int offset) {
    this.left += offset;
    this.right += offset;
    return true;
  }

  @Implementation
  protected boolean offsetTopAndBottom(int offset) {
    this.top += offset;
    this.bottom += offset;
    return true;
  }

  @Implementation
  protected void getInverseMatrix(Matrix matrix) {
    getMatrix(matrix);
    matrix.invert(matrix);
  }

  @Implementation
  protected void getMatrix(Matrix matrix) {
    if (!pivotExplicitlySet) {
      pivotX = getWidth() / 2f;
      pivotY = getHeight() / 2f;
    }
    matrix.reset();
    if (isZero(rotationX) && isZero(rotationY)) {
      matrix.setTranslate(translationX, translationY);
      matrix.preRotate(rotation, pivotX, pivotY);
      matrix.preScale(scaleX, scaleY, pivotX, pivotY);
    } else {
      matrix.preScale(scaleX, scaleY, pivotX, pivotY);
      Camera camera = new Camera();
      camera.rotateX(rotationX);
      camera.rotateY(rotationY);
      camera.rotateZ(-rotation);
      Matrix transform = new Matrix();
      camera.getMatrix(transform);
      transform.preTranslate(-pivotX, -pivotY);
      transform.postTranslate(pivotX + translationX, pivotY + translationY);
      matrix.postConcat(transform);
    }
  }

  @Implementation
  protected boolean hasIdentityMatrix() {
    Matrix matrix = new Matrix();
    getMatrix(matrix);
    return matrix.isIdentity();
  }

  @Implementation
  protected boolean isValid() {
    return true;
  }

  /**
   * Implementation of native method nSetLayerType
   *
   * @param renderNode Ignored
   * @param layerType Ignored
   * @return Always true
   */
  @Implementation
  protected static boolean nSetLayerType(long renderNode, int layerType) {
    return true;
  }

  /**
   * Implementation of native method nSetLayerPaint
   *
   * @param renderNode Ignored
   * @param paint Ignored
   * @return Always true
   */
  @Implementation
  protected static boolean nSetLayerPaint(long renderNode, long paint) {
    return true;
  }

  private static boolean isZero(float value) {
    return Math.abs(value) <= NON_ZERO_EPSILON;
  }
}
