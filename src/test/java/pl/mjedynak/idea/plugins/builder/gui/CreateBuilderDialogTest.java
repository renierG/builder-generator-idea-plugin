package pl.mjedynak.idea.plugins.builder.gui;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.ReferenceEditorComboWithBrowseButton;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.mjedynak.idea.plugins.builder.factory.ReferenceEditorComboWithBrowseButtonFactory;
import pl.mjedynak.idea.plugins.builder.helper.PsiHelper;

import javax.swing.*;
import java.awt.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CreateBuilderDialogTest {

    private CreateBuilderDialog createBuilderDialog;

    @Mock
    private Project project;

    @Mock
    private PsiPackage targetPackage;

    @Mock
    private Module targetModule;

    @Mock
    private PsiHelper psiHelper;

    @Mock
    private GuiHelper guiHelper;

    @Mock
    private PsiManager psiManager;

    @Mock
    private ReferenceEditorComboWithBrowseButtonFactory referenceEditorComboWithBrowseButtonFactory;

    @Mock
    private ReferenceEditorComboWithBrowseButton referenceEditorComboWithBrowseButton;

    private String className;

    @Before
    public void setUp() {
        String title = "title";
        className = "className";
        String packageName = "packageName";
        given(targetPackage.getQualifiedName()).willReturn(packageName);
        given(referenceEditorComboWithBrowseButtonFactory.getReferenceEditorComboWithBrowseButton(
                psiManager, packageName, CreateBuilderDialog.RECENTS_KEY)).willReturn(referenceEditorComboWithBrowseButton);

        createBuilderDialog = new CreateBuilderDialog(
                project, title, className, targetPackage, targetModule, psiHelper, guiHelper, psiManager, referenceEditorComboWithBrowseButtonFactory);
    }


    @Test
    public void shouldReturnTargetClassNameFromValueGivenInConstructor() {
        // when
        String result = createBuilderDialog.getClassName();

        // then
        assertThat(result, is(className));
    }

    @Test
    public void shouldReturnPreferredFocusedComponentAsJTextFieldWithClassName() {
        // when
        JComponent result = createBuilderDialog.getPreferredFocusedComponent();

        // then
        assertThat(result, is(instanceOf(JTextField.class)));
        assertThat(((JTextField) result).getText(), is(className));
    }

    @Test
    public void shouldReturnTargetDirectoryAsNullWhenOkActionWasntClicked() {
        // when
        PsiDirectory result = createBuilderDialog.getTargetDirectory();

        // then
        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldSetTargetDirectory() {
        // given
        PsiDirectory targetDirectory = mock(PsiDirectory.class);

        // when
        createBuilderDialog.setTargetDirectory(targetDirectory);

        // then
        assertThat(createBuilderDialog.getTargetDirectory(), is(targetDirectory));
    }

    @Test
    public void shouldCreateJPanelWithComponentsAndGridBagLayout() {
        // when
        JComponent centerPanel = createBuilderDialog.createCenterPanel();

        // then
        assertThat(centerPanel, is(instanceOf(JPanel.class)));
        assertThat(centerPanel.getComponentCount() > 0,is(true));
        assertThat(centerPanel.getLayout(), is(instanceOf(GridBagLayout.class)));
    }

    @Test
    public void shouldCreateThreeActions() {
        // when
        Action[] actions = createBuilderDialog.createActions();

        // then
        assertThat(actions.length, is(3));
    }
}

