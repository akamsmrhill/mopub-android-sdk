// Copyright 2018-2021 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// https://www.mopub.com/legal/sdk-license-agreement/

package com.mopub.mobileads;

import com.mopub.common.test.support.SdkTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.mopub.mobileads.VastResource.fromVastResourceXmlManager;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SdkTestRunner.class)
public class VastResourceTest {

    private VastResource subject;
    @Mock private VastWebView mockVastWebView;

    @Before
    public void setup() {
        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE,
                VastResource.CreativeType.IMAGE, 50, 100);
    }

    @Test
    public void fromVastResourceXmlManager_withIFrameType_shouldSetIFrameResource() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", "image/jpeg", "IFrameResource", "HTMLResource");

        VastResource vastResource =
                fromVastResourceXmlManager(resourceXmlManager, VastResource.Type.IFRAME_RESOURCE,
                        50, 100);

        assertThat(vastResource.getResource()).isEqualTo("IFrameResource");
        assertThat(vastResource.getType())
                .isEqualTo(VastResource.Type.IFRAME_RESOURCE);
        assertThat(vastResource.getCreativeType())
                .isEqualTo(VastResource.CreativeType.NONE);
    }

    @Test
    public void fromVastResourceXmlManager_withHTMLType_shouldSetHTMLResource() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", "image/jpeg", "IFrameResource", "HTMLResource");

        VastResource vastResource =
                fromVastResourceXmlManager(resourceXmlManager, VastResource.Type.HTML_RESOURCE, 50,
                        100);

        assertThat(vastResource.getResource()).isEqualTo("HTMLResource");
        assertThat(vastResource.getType())
                .isEqualTo(VastResource.Type.HTML_RESOURCE);
        assertThat(vastResource.getCreativeType())
                .isEqualTo(VastResource.CreativeType.NONE);
    }

    @Test
    public void fromVastResourceXmlManager_withStaticType_withImageCreativeType_shouldSetImageCreativeType() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", "image/jpeg", "IFrameResource", "HTMLResource");

        VastResource vastResource = fromVastResourceXmlManager(resourceXmlManager,
                VastResource.Type.STATIC_RESOURCE, 50, 100);

        assertThat(vastResource.getResource()).isEqualTo("StaticResource");
        assertThat(vastResource.getType())
                .isEqualTo(VastResource.Type.STATIC_RESOURCE);
        assertThat(vastResource.getCreativeType())
                .isEqualTo(VastResource.CreativeType.IMAGE);
    }

    @Test
    public void fromVastResourceXmlManager_withStaticType_withJavaScriptCreativeType_shouldSetJavascriptCreativeType() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", "application/x-javascript", "IFrameResource", "HTMLResource");

        VastResource vastResource = fromVastResourceXmlManager(resourceXmlManager,
                VastResource.Type.STATIC_RESOURCE, 50, 100);

        assertThat(vastResource.getResource()).isEqualTo("StaticResource");
        assertThat(vastResource.getType())
                .isEqualTo(VastResource.Type.STATIC_RESOURCE);
        assertThat(vastResource.getCreativeType())
                .isEqualTo(VastResource.CreativeType.JAVASCRIPT);
    }

    @Test
    public void fromVastResourceXmlManager_withStaticType_withMissingCreativeType_shouldReturnNull() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", null, "IFrameResource", "HTMLResource");

        assertThat(fromVastResourceXmlManager(
                resourceXmlManager, VastResource.Type.STATIC_RESOURCE, 50, 100)).isNull();
    }

    @Test
    public void fromVastResourceXmlManager_withStaticType_withInvalidCreativeType_shouldReturnNull() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        "StaticResource", "INVALID_CREATIVE_TYPE", "IFrameResource", "HTMLResource");

        assertThat(fromVastResourceXmlManager(
                resourceXmlManager, VastResource.Type.STATIC_RESOURCE, 50, 100)).isNull();
    }

    @Test
    public void fromVastResourceXmlManager_withNoResource_shouldReturnNull() {
        final VastResourceXmlManager resourceXmlManager =
                VastXmlManagerAggregatorTest.initializeVastResourceXmlManagerMock(
                        null, null, null, null);

        assertThat(fromVastResourceXmlManager(
                resourceXmlManager, VastResource.Type.STATIC_RESOURCE, 50, 100)).isNull();
    }

    @Test
    public void constructor_shouldInitializeFieldsCorrectly() {
        assertThat(subject.getResource()).isEqualTo("resource");
        assertThat(subject.getType()).isEqualTo(VastResource.Type.STATIC_RESOURCE);
        assertThat(subject.getCreativeType()).isEqualTo(VastResource.CreativeType.IMAGE);
    }

    @Test
    public void initializeWebView_withIFrameResource_shouldLoadData() {
        subject = new VastResource("resource", VastResource.Type.IFRAME_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        subject.initializeWebView(mockVastWebView);

        verify(mockVastWebView).loadData(
                "<iframe frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" " +
                        "marginwidth=\"0\" style=\"border: 0px; margin: 0px;\" width=\"50\" " +
                        "height=\"100\" src=\"resource\"></iframe>");
    }

    @Test
    public void initializeWebView_withHTMLResource_shouldLoadData() {
        subject = new VastResource("resource", VastResource.Type.HTML_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        subject.initializeWebView(mockVastWebView);

        verify(mockVastWebView).loadData("resource");
    }

    @Test
    public void initializeWebView_withStaticResource_withImageCreativeType_shouldLoadData() {
        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE, VastResource
                .CreativeType.IMAGE, 50, 100);
        subject.initializeWebView(mockVastWebView);

        verify(mockVastWebView).loadData("resource");
    }

    @Test
    public void initializeWebView_withStaticResource_withJavascriptCreativeType_shouldLoadData() {
        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE, VastResource
                .CreativeType.JAVASCRIPT, 50, 100);
        subject.initializeWebView(mockVastWebView);

        verify(mockVastWebView).loadData("<script src=\"resource\"></script>");
    }

    @Test
    public void initializeWebView_withBlurredLastFrameResource_withImageCreativeType_shouldLoadData() {
        subject = new VastResource("resource", VastResource.Type.BLURRED_LAST_FRAME, VastResource
                .CreativeType.IMAGE, 50, 100);
        subject.initializeWebView(mockVastWebView);

        verify(mockVastWebView).loadData("resource");
    }

    @Test
    public void getCorrectClickThroughUrl_shouldReturnCorrectClickThroughUrl() {
        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE, VastResource
                .CreativeType.IMAGE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", "web")).isEqualTo("xml");

        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE, VastResource
                .CreativeType.JAVASCRIPT, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", "web")).isEqualTo("web");

        subject = new VastResource("resource", VastResource.Type.HTML_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", "web")).isEqualTo("web");

        subject = new VastResource("resource", VastResource.Type.IFRAME_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", "web")).isEqualTo("web");

        subject = new VastResource("resource", VastResource.Type.HTML_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", null)).isNull();

        subject = new VastResource("resource", VastResource.Type.IFRAME_RESOURCE, VastResource
                .CreativeType.NONE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", null)).isNull();

        subject = new VastResource("resource", VastResource.Type.STATIC_RESOURCE, VastResource
                .CreativeType.IMAGE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", null)).isEqualTo("xml");

        subject = new VastResource("resource", VastResource.Type.BLURRED_LAST_FRAME, VastResource
                .CreativeType.IMAGE, 50, 100);
        assertThat(subject.getCorrectClickThroughUrl("xml", null)).isEqualTo("xml");
    }
}
