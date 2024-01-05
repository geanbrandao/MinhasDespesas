package dev.geanbrandao.minhasdespesas.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import br.dev.geanbrandao.common.presentation.BaseScreen
import br.dev.geanbrandao.common.presentation.components.RowFieldView
import br.dev.geanbrandao.common.presentation.components.button.ButtonType
import br.dev.geanbrandao.common.presentation.components.button.ButtonView
import br.dev.geanbrandao.common.presentation.components.icon.IconType
import br.dev.geanbrandao.common.presentation.components.icon.IconView
import br.dev.geanbrandao.common.presentation.components.inputs.InputView
import br.dev.geanbrandao.common.presentation.components.inputs.InputViewData
import br.dev.geanbrandao.common.presentation.components.inputs.InputViewState
import br.dev.geanbrandao.common.presentation.components.spacer.SpacerThree
import br.dev.geanbrandao.common.presentation.components.text.TextDefault
import br.dev.geanbrandao.common.presentation.components.toolbar.ToolbarView
import br.dev.geanbrandao.common.presentation.resources.PaddingThree
import br.dev.geanbrandao.common.presentation.resources.PaddingTwo
import dev.geanbrandao.minhasdespesas.R
import dev.geanbrandao.minhasdespesas.domain.model.Category
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    popBackWithResult: (argument: String?) -> Unit,
    goBack: () -> Unit,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.getCategories()
        }
    }

    val categories = viewModel.categories.collectAsState()

    CategoriesScreenView(
        categories = categories.value,
        createNewCategory = { newCategory: String ->
            viewModel.addNewCategory(newCategory)
        },
        onCategoryRemoved = { id: Long ->
            viewModel.removeCategory(categoryId = id)
        },
        onCategoryEdit = { id: Long ->

        },
        onCheckChangeListener = { isChecked: Boolean, item: Category ->
            viewModel.updateSelectedCategories(categoryId = item.categoryId, isChecked = isChecked)
        },
        onNavigateBackToAddExpense = {
//            popBackWithResult(buildArgument(categories.value))
             viewModel.navigateBack(buildArgument(categories.value))
        },
        onBackButtonClicked = { viewModel.navigateBack() },
    )
}

private fun buildArgument(categories: List<Category>) : List<Long> {
    return categories // "1,2" ou null
        .filter { it.isChecked }
        .map { it.categoryId }
        .takeIf { it.isNotEmpty() }.orEmpty()
}

@Composable
private fun CategoriesScreenView(
    categories: List<Category>,
    createNewCategory: (categoryName: String) -> Unit,
    onCategoryRemoved: (categoryId: Long) -> Unit,
    onCategoryEdit: (categoryId: Long) -> Unit,
    onCheckChangeListener: (isChecked: Boolean, item: Category) -> Unit,
    onNavigateBackToAddExpense: () -> Unit,
    onBackButtonClicked: () -> Unit = {},
) {
    BaseScreen(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        header = {
            ToolbarView(
                onBackButtonClicked = onBackButtonClicked,
                toolbarTitle = stringResource(id = R.string.fragment_add_edit_expense_text_select_the_categories)
            )
        },
        content = {
            CreateCategoryView(createNewCategory)
            ListCategoryDefaultView(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = PaddingTwo),
                list = categories,
                onDeleteClicked = onCategoryRemoved,
                onEditClicked = onCategoryEdit,
                onCheckChangeListener = onCheckChangeListener
            )
        },
        footer = {
            ButtonView(
                text = stringResource(id = R.string.button_default_text_select),
                modifier = Modifier.padding(
                    start = PaddingTwo,
                    end = PaddingTwo,
                    bottom = PaddingThree
                ),
                onClick = onNavigateBackToAddExpense
            )
        }
    )
}

@Composable
private fun CreateCategoryView(
    createNewCategory: (categoryName: String) -> Unit,
) {

    val color = MaterialTheme.colorScheme.secondary
    val dialogIsVisible = remember { mutableStateOf(false) }

    RowFieldView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = PaddingTwo),
        start = {
            IconView(
                icon = painterResource(id = R.drawable.ic_add),
                tint = color,
                iconType = IconType.Input,
            )
        },
        content = {
            TextDefault(
                text = stringResource(id = R.string.fragment_categoires_text_label_top),
                textColor = color,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        },
        end = {
            IconView(
                icon = painterResource(id = R.drawable.ic_arrow_right),
                tint = color,
                iconType = IconType.Default,
            )
        },
        onClick = {
            dialogIsVisible.value = true
        }
    )

    if (dialogIsVisible.value) {
        CreateCategoryDialog(
            onCloseDialog = {
                dialogIsVisible.value = false
            },
            onConfirm = { newCategoryName ->
                dialogIsVisible.value = false
                createNewCategory(newCategoryName)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateCategoryDialog(
    onCloseDialog: () -> Unit,
    onConfirm: (categoryName: String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputViewState = InputViewState(
        textInput = "",
        textLabel = stringResource(id = R.string.dialog_create_category_text_title),
    )
    AlertDialog(
        onDismissRequest = onCloseDialog
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InputView(
                    inputViewState = inputViewState,
                    inputViewData = InputViewData(),
                )
                SpacerThree()
                Row(modifier = Modifier.align(Alignment.End)) {
                    ButtonView(
                        text = "Confirmar",
                        onClick = {
                            keyboardController?.hide()
                            onConfirm(inputViewState.textInput.value.text)
                        },
                        buttonType = ButtonType.Dialog,
                        isEnabled = inputViewState.textInput.value.text.isNotEmpty()
                    )
                    ButtonView(
                        text = "Cancelar",
                        onClick = {
                            keyboardController?.hide()
                            onCloseDialog()
                        },
                        buttonType = ButtonType.Dialog,
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun CategoriesScreenViewPreview() {
    CategoriesScreenView(
        categories = createCategoryHelper(),
        createNewCategory = {

        },
        onCategoryRemoved = {

        },
        onCategoryEdit = {

        },
        onCheckChangeListener = { isChecked: Boolean, item: Category ->

        },
        onNavigateBackToAddExpense = {

        }
    )
}